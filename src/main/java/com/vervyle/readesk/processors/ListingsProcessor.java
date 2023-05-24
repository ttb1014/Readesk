package com.vervyle.readesk.processors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@PropertySource("classpath:application.properties")
public class ListingsProcessor {
    @Value("${listingsProcessor.dataSourceCoins}")
    private String dataSourceCoins;
    @Value("${listingsProcessor.dataSourceMarkets}")
    private String dataSourceMarkets;

    private Scanner makeScanner(String dataSource) throws FileNotFoundException, RuntimeException {
        File file = new File(dataSource);
        Scanner scanner;
        InputStream dataStream = new FileInputStream(file);
        scanner = new Scanner(dataStream, StandardCharsets.UTF_8);
        String header = scanner.nextLine();
        String[] colNames = header.split(",");
        if (colNames.length != 2)
            throw new RuntimeException("Column header is corrupted!");
        return scanner;
    }

    /**
     * @return list of supported coins. May be empty
     */
    public List<String> getListedCoins() {
        List<String> result = new LinkedList<>();
        try {
            Scanner scanner = makeScanner(dataSourceCoins);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line.split(",")[0]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot update listed coins. File not found: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Cannot update listed coins. Runtime exception: " + e.getMessage());
        }
        return result;
    }

    /**
     * @return supported listing markets. May be empty
     */
    public List<String> getListingMarkets() {
        List<String> result = new LinkedList<>();
        try {
            Scanner scanner = makeScanner(dataSourceMarkets);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line.split(",")[0]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot update markets. File not found: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Cannot update markets. Runtime exception: " + e.getMessage());
        }
        return result;
    }
}
