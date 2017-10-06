package com.whitewoodcity.core.node.chart;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import javafx.scene.chart.*;

public class XYChart extends Chart{

    final Axis xAxis;
    final Axis yAxis;
    private JsonArray xScale = new JsonArray("[1,2,3,4,5,6,7,8,9,10]");

    public XYChart(String type,AxisType xAxisType,AxisType yAxisType){

        switch (xAxisType){
            case CATEGORY:
                xAxis = new CategoryAxis();
                break;
            default:
                xAxis = new NumberAxis();
                break;
        }

        switch (yAxisType){
            case CATEGORY:
                yAxis = new CategoryAxis();
                break;
            default:
                yAxis = new NumberAxis();
                break;
        }

        switch (type){
            case "barchart":
                body = new BarChart(xAxis,yAxis);
                break;
            case "areachart":
                body = new AreaChart(xAxis,yAxis);
                break;
            case "bubblechart":
                body = new BubbleChart(xAxis,yAxis);
                break;
            case "scatterchart":
                body = new ScatterChart(xAxis,yAxis);
                break;
            default:
                body = new LineChart(xAxis,yAxis);
                break;
        }

    }

    public void setXLabel(String label){
        xAxis.setLabel(label);
    }

    public void setYLabel(String label){
        yAxis.setLabel(label);
    }

    public void setXScale(JsonArray xScale){
        if(xScale==null) return;
        this.xScale = xScale;
    }

    public void setSeries(String name, JsonArray jsonArray){

        javafx.scene.chart.XYChart.Series series = new javafx.scene.chart.XYChart.Series();
        series.setName(name);
        if(xScale == null) return;
        for(int i=0;i<xScale.size();i++){
            if(i<jsonArray.size()&&jsonArray.getValue(i)!=null) {
                if(xAxis instanceof CategoryAxis)
                    series.getData().add(new javafx.scene.chart.XYChart.Data(xScale.getValue(i).toString(), jsonArray.getValue(i)));
                else
                    series.getData().add(new javafx.scene.chart.XYChart.Data(xScale.getValue(i), jsonArray.getValue(i)));
            }
        }
        ((javafx.scene.chart.XYChart)body).getData().add(series);
    }

    public void setData(JsonObject jsonObject){
        for(String name:jsonObject.fieldNames()){
            if(jsonObject.getValue(name)!=null && jsonObject.getValue(name) instanceof JsonArray){
                setSeries(name, jsonObject.getJsonArray(name));
            }
        }
    }
}
