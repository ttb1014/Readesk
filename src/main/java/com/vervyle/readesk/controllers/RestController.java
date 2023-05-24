package com.vervyle.readesk.controllers;

import com.vervyle.readesk.DAOs.ChartType;
import com.vervyle.readesk.services.ChartDataService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serial;

@Controller
@RequestMapping(value = "/restfull-service")
public class RestController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    private ChartDataService chartDataService;

    @Autowired
    public void setChartDataService(ChartDataService chartDataService) {
        this.chartDataService = chartDataService;
    }

    @GetMapping("/nasdaq-quotes")
    public @ResponseBody String getCryptoUnits(@RequestParam("coinName") String coinName,
                                               @RequestParam("marketName") String marketName,
                                               @RequestParam("chartType") ChartType chartType) {
        return chartDataService.getChartData(coinName, marketName, chartType);
    }
}