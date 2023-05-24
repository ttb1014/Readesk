package com.vervyle.readesk.DAOs;

import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoResponse;
import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;
import com.vervyle.readesk.model.ChartData;
import org.json.JSONArray;
import org.json.JSONObject;
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

//    private JSONArray getJSONData(ChartType chartType, List<CryptoUnit> cryptoUnits) {
//        JSONArray jsonData = new JSONArray();
//        for (CryptoUnit cryptoUnit : cryptoUnits) {
//            JSONObject jsonObject = new JSONObject()
//                    .put("x", cryptoUnit.getDate());
//            switch (chartType) {
//                case LOW -> jsonObject.put("y", cryptoUnit.getLow());
//                case HIGH -> jsonObject.put("y", cryptoUnit.getHigh());
//                case VOLUME -> jsonObject.put("y", cryptoUnit.getVolume());
//                default -> System.out.println("Cannot find this field in cryptoUnit!");
//            }
//            jsonData.put(jsonObject);
//        }
//        return jsonData;
//    }
//
//    public String getChartData(String coinName, String marketName, ChartType chartType) {
//        List<CryptoUnit> cryptoUnits = getData(coinName, marketName);
//        return getJSONData(chartType, cryptoUnits).toString();
//    }
}
