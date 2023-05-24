package com.vervyle.readesk.services;

import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;
import com.vervyle.readesk.DAOs.ChartDataDao;
import com.vervyle.readesk.DAOs.ChartType;
import com.vervyle.readesk.processors.ChartDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChartDataService {

    private ChartDataDao chartDataDao;
    private ChartDataProcessor chartDataProcessor;
    private final Map<String, List<CryptoUnit>> bufferedData;

    @Autowired
    public ChartDataService() {
        bufferedData = new HashMap<>();
    }

    @Autowired
    public void setChartDataDao(ChartDataDao chartDataDao) {
        this.chartDataDao = chartDataDao;
    }

    @Autowired
    public void setChartDataProcessor(ChartDataProcessor chartDataProcessor) {
        this.chartDataProcessor = chartDataProcessor;
    }

    public String getChartData(String coinName, String marketName, ChartType chartType) {
        String key = coinName + "/" + marketName;
        if (bufferedData.containsKey(key)) {
            System.out.println("Successfully got ChartData from cache! " + key + "/" + chartType);
            return chartDataProcessor.getChartData(bufferedData.get(key), chartType);
        }
        List<CryptoUnit> cryptoUnits = chartDataDao.getData(coinName, marketName);
        bufferedData.put(key, cryptoUnits);
        System.out.println("Successfully got ChartData from DAO! " + key + "/" + chartType);
        return chartDataProcessor.getChartData(bufferedData.get(key), chartType);
    }

    public List<CryptoUnit> getData(String coinName, String marketName) {
        List<CryptoUnit> cryptoUnits;
        String key = coinName + "/" + marketName;
        if (bufferedData.containsKey(key)) {
            System.out.println("Successfully got Data from cache! " + key);
            cryptoUnits = bufferedData.get(key);
            return cryptoUnits;
        }
        cryptoUnits = chartDataDao.getData(coinName, marketName);
        bufferedData.put(key, cryptoUnits);
        System.out.println("Successfully got Data from DAO! " + key);
        return cryptoUnits;
    }
}
