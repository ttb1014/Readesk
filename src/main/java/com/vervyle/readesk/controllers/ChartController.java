package com.vervyle.readesk.controllers;

import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;
import com.vervyle.readesk.DAOs.ChartType;
import com.vervyle.readesk.services.ChartDataService;
import com.vervyle.readesk.services.ListingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChartController {

    private ChartDataService chartDataService;
    private ListingsService listingsService;

    @Autowired
    public void setChartDataService(ChartDataService chartDataService) {
        this.chartDataService = chartDataService;
    }

    @Autowired
    public void setListingsService(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @GetMapping("/chart")
    public String chart(Model model,
                        @RequestParam(value = "coinName", required = false) String coinName,
                        @RequestParam(value = "marketName", required = false) String marketName,
                        @RequestParam(value = "chartType", required = false) ChartType chartType) {
        List<CryptoUnit> cryptoUnits = chartDataService.getData(coinName, marketName);
        List<String> coins = listingsService.getCoins();
        List<String> markets = listingsService.getMarkets();
        model.addAttribute("cryptoUnits", cryptoUnits);
        model.addAttribute("coinName", coinName);
        model.addAttribute("marketName", marketName);
        model.addAttribute("chartType", chartType);
        model.addAttribute("coins", coins);
        model.addAttribute("markets", markets);
        model.addAttribute("chartTypes", ChartType.values());
        return "chart";
    }
}
