package com.whitewoodcity.core.node.chart;

import io.vertx.core.json.JsonObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PieChart extends Chart{
    public PieChart(){
        if (Platform.isFxApplicationThread()) body = new javafx.scene.chart.PieChart();
        else Platform.runLater(() -> body = new javafx.scene.chart.PieChart());
    }

    ObservableList<javafx.scene.chart.PieChart.Data> pieChartData =
            FXCollections.observableArrayList();

    public void setData(JsonObject data){
        if(Platform.isFxApplicationThread()) setDataBase(data);
        else Platform.runLater(()->setDataBase(data));
    }
    private void setDataBase(JsonObject data){

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

    public void set(String key, double value){
        if(Platform.isFxApplicationThread()) setBase(key,value);
        else Platform.runLater(()->setBase(key,value));
    }
    private void setBase(String key, double value){
        for(javafx.scene.chart.PieChart.Data datum:pieChartData){
            if(datum.getName().equals(key)){
                datum.setPieValue(value);
                return;
            }
        }
        pieChartData.add(new javafx.scene.chart.PieChart.Data(key, value));
    }

    public void remove(String key){
        if(Platform.isFxApplicationThread()) removeBase(key);
        else Platform.runLater(()->removeBase(key));
    }
    private void removeBase(String key){
        javafx.scene.chart.PieChart.Data d=null;
        for(javafx.scene.chart.PieChart.Data datum:pieChartData){
            if(datum.getName().equals(key)){
                d = datum;
            }
        }

        if(d!=null)
            pieChartData.remove(d);
    }
}
