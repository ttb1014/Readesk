package com.vervyle.readesk.processors;

import com.crazzyghost.alphavantage.cryptocurrency.response.CryptoUnit;
import com.vervyle.readesk.DAOs.ChartType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChartDataProcessor {
    public String getChartData(List<CryptoUnit> cryptoUnits, ChartType chartType) {
        return getJSONChartData(cryptoUnits, chartType).toString();
    }

    private JSONArray getJSONChartData(List<CryptoUnit> cryptoUnits, ChartType chartType) {
        JSONArray jsonData = new JSONArray();
        for (CryptoUnit cryptoUnit : cryptoUnits) {
            JSONObject jsonObject = new JSONObject()
                    .put("x", cryptoUnit.getDate());
            switch (chartType) {
                case LOW -> jsonObject.put("y", cryptoUnit.getLow());
                case HIGH -> jsonObject.put("y", cryptoUnit.getHigh());
                case VOLUME -> jsonObject.put("y", cryptoUnit.getVolume());
                default -> System.out.println("Cannot find this field in cryptoUnit!");
            }
            jsonData.put(jsonObject);
        }
        return jsonData;
    }
}
