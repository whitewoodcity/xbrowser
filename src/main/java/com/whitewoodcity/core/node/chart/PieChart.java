package com.whitewoodcity.core.node.chart;

import io.vertx.core.json.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PieChart extends Chart{
    public PieChart(){
        body = new javafx.scene.chart.PieChart();
    }

    public void setData(JsonObject data){

        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData =
                FXCollections.observableArrayList();

        for(String key:data.fieldNames()){
            if(data.getValue(key)==null) continue;
            if(data.getValue(key) instanceof Number){
                pieChartData.add(new javafx.scene.chart.PieChart.Data(key, ((Number)data.getValue(key)).doubleValue()));
            }else{
                try{
                    double value = Double.parseDouble(data.getValue(key).toString());
                    pieChartData.add(new javafx.scene.chart.PieChart.Data(key, value));
                }catch (Exception e){
                    //do nothing
                }
            }
        }

        ((javafx.scene.chart.PieChart)body).setData(pieChartData);
    }
}
