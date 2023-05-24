package com.vervyle.readesk.DAOs;

import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;

import java.util.List;

public interface ChartDataDao {
    public String getChartData(String coinName, String marketName, ChartType chartType);

    public List<CryptoUnit> getData(String coinName, String marketName);
}
