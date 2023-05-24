package com.vervyle.readesk.services;

import com.vervyle.readesk.processors.ListingsProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingsService {

    private ListingsProcessor listingsProcessor;

    @Autowired
    public void setListingsProcessor(ListingsProcessor listingsProcessor) {
        this.listingsProcessor = listingsProcessor;
    }

    public static class Listing {
        private String coin;
        private String market;

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public Listing(String coin, String market) {
            this.coin = coin;
            this.market = market;
        }
    }

    public List<String> getCoins() {
        return listingsProcessor.getListedCoins();
    }

    public List<String> getMarkets() {
        return listingsProcessor.getListingMarkets();
    }

    public List<Listing> getListings() {
        List<Listing> result = new ArrayList<>();
        List<String> coins = listingsProcessor.getListedCoins();
        List<String> markets = listingsProcessor.getListingMarkets();
        int size = Math.max(coins.size(), markets.size());
        for (int i = 0; i < size; i++) {
            String coin = "";
            String market = "";
            if (i < coins.size())
                coin = coins.get(i);
            if (i < markets.size())
                market = markets.get(i);
            result.add(new Listing(coin, market));
        }
        return result;
    }
}
