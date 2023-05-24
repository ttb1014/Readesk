package com.vervyle.readesk.services;

import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;
import com.vervyle.readesk.DAOs.ChartDataDao;
import com.vervyle.readesk.DAOs.ChartType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartDataService {

    ChartDataDao chartDataDao;

    @Autowired
    public void setChartDataDao(ChartDataDao chartDataDao) {
        this.chartDataDao = chartDataDao;
    }

    public String getChartData(String coinName, String marketName, ChartType chartType) {
        System.out.println("Successfully got String data!");
        return chartDataDao.getChartData(coinName, marketName, chartType);
    }

    public List<CryptoUnit> getData(String coinName, String marketName) {
        System.out.println("Successfully got List of data!");
        return chartDataDao.getData(coinName, marketName);
    }
}
