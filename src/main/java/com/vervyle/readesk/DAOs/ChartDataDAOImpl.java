package com.vervyle.readesk.DAOs;

import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoResponse;
import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;
import com.vervyle.readesk.model.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChartDataDAOImpl implements ChartDataDao {

    private ChartData chartData;
    private List<CryptoUnit> result;
    private volatile boolean flag = false;

    @Autowired
    public void setDataService(ChartData chartData) {
        this.chartData = chartData;
    }

    @Override
    public List<CryptoUnit> getData(String coinName, String marketName) {
        chartData.setSuccessCallback(o -> {
            result = ((CryptoResponse) o).getCryptoUnits();
            flag = true;
        });
        chartData.getDailyStocks(coinName, marketName);
        while (!flag) {
            Thread.onSpinWait();
        }
        return result;
    }
}
