package com.vervyle.readesk.model;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.Fetcher;
import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoResponse;
import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;
import com.vervyle.readesk.processors.ListingsProcessor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChartData {
    private Fetcher.SuccessCallback<Object> successCallback;
    private Fetcher.FailureCallback failureCallback;
    private final List<String> listedCoins;
    private final List<String> listingMarkets;
    private final ListingsProcessor listingsProcessor;

    @Value("${chartData.APIKey}")
    private String APIKey;

    public void setSuccessCallback(Fetcher.SuccessCallback<Object> successCallback) {
        this.successCallback = successCallback;
    }

    public void setFailureCallback(Fetcher.FailureCallback failureCallback) {
        this.failureCallback = failureCallback;
    }

    @Autowired
    public ChartData(ListingsProcessor listingsProcessor) {
        failureCallback = new Fetcher.FailureCallback() {
            @Override
            public void onFailure(AlphaVantageException e) {
                System.out.println("Error has occurred while getting data from AlphaVantage API" + e.getMessage());
            }
        };
        successCallback = new Fetcher.SuccessCallback<Object>() {
            @Override
            public void onSuccess(Object o) {
                List<CryptoUnit> list = ((CryptoResponse) o).getCryptoUnits();
                for (CryptoUnit unit : list) {
                    System.out.println(unit.toString());
                }
            }
        };
        this.listingsProcessor = listingsProcessor;
        listedCoins = listingsProcessor.getListedCoins();
        listingMarkets = listingsProcessor.getListingMarkets();
    }

    @PostConstruct
    public void postInit() {
        Config cfg = Config.builder()
                .key(APIKey)
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
    }

    public void getDailyStocks(String coinName, String marketName) {
        if (!listingMarkets.contains(marketName) || !listedCoins.contains(coinName))
            throw new RuntimeException("Cannot find any data with params: " + coinName + ", " + marketName);

        AlphaVantage.api()
                .crypto()
                .daily()
                .forSymbol(coinName)
                .market(marketName)
                .onSuccess(successCallback)
                .onFailure(failureCallback)
                .fetch();
    }
}
