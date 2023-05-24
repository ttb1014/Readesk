package com.vervyle.readesk.DAOs;

import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;

import java.util.List;

public interface ChartDataDao {

    List<CryptoUnit> getData(String coinName, String marketName);
}
