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

    private void setSeries(String name, JsonArray jsonArray){

        javafx.scene.chart.XYChart.Series series = new javafx.scene.chart.XYChart.Series();
        series.setName(name);
        if(xScale == null) return;
        for(int i=0;i<xScale.size();i++){
            if(i<jsonArray.size()&&jsonArray.getValue(i)!=null) {
                Object x = xScale.getValue(i);
                if(xAxis instanceof CategoryAxis)
                    x = x.toString();

                Object y = jsonArray.getValue(i);
                if(y == null){

                }else if(y instanceof Number)
                    series.getData().add(new javafx.scene.chart.XYChart.Data(x, y));
                else if(y instanceof JsonObject){
                    Object xx=null, yy=null, extra=null;
                    JsonObject jsonObject = (JsonObject)y;
                    for(String key:jsonObject.fieldNames()){
                        if(key.equals("x")) xx = jsonObject.getValue(key);
                        else if(key.equals("y")) yy = jsonObject.getValue(key);
                        else extra = jsonObject.getValue(key);
                    }

                    if(xx!=null&&yy!=null){
                        if(extra!=null) series.getData().add(new javafx.scene.chart.XYChart.Data(xx, yy, extra));
                        else series.getData().add(new javafx.scene.chart.XYChart.Data(xx, yy));
                    }
                }
                else
                    series.getData().add(new javafx.scene.chart.XYChart.Data(x, y.toString()));
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

    public void set(String series, Object x, Object y){
        for(Object object:((javafx.scene.chart.XYChart)body).getData()){
            javafx.scene.chart.XYChart.Series s = (javafx.scene.chart.XYChart.Series)object;
            if(s.getName().equals(series)){
                for(Object o:s.getData()){
                    javafx.scene.chart.XYChart.Data datum =(javafx.scene.chart.XYChart.Data)o;
                    if(x.equals(datum.getXValue())){
                        datum.setYValue(y);
                        return;
                    }
                }
                s.getData().add(new javafx.scene.chart.XYChart.Data(x, y));
                return;
            }
        }
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("x",x).put("y",y);
        jsonArray.add(jsonObject);
        setSeries(series,jsonArray);
    }

    public void set(String series, Object x, Object y, Object e){
        for(Object object:((javafx.scene.chart.XYChart)body).getData()){
            javafx.scene.chart.XYChart.Series s = (javafx.scene.chart.XYChart.Series)object;
            if(s.getName().equals(series)){
                for(Object o:s.getData()){
                    javafx.scene.chart.XYChart.Data datum =(javafx.scene.chart.XYChart.Data)o;
                    if(x.equals(datum.getXValue())&&y.equals(datum.getYValue())){
                        datum.setExtraValue(e);
                        return;
                    }
                }
                s.getData().add(new javafx.scene.chart.XYChart.Data(x, y, e));
                return;
            }
        }
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("x",x).put("y",y).put("e",e);
        jsonArray.add(jsonObject);
        setSeries(series,jsonArray);
    }

    public void remove(String series, Object x, Object y){
        for(Object object:((javafx.scene.chart.XYChart)body).getData()){
            javafx.scene.chart.XYChart.Series s = (javafx.scene.chart.XYChart.Series)object;
            if(s.getName().equals(series)){
                Object tobeRemoved = null;
                for(Object o:s.getData()){
                    javafx.scene.chart.XYChart.Data datum =(javafx.scene.chart.XYChart.Data)o;
                    if(x.equals(datum.getXValue())&&y.equals(datum.getYValue())){
                        tobeRemoved = datum;
                        break;
                    }
                }
                if(tobeRemoved!=null){
                    s.getData().remove(tobeRemoved);
                    return;
                }
            }
        }
    }

    public void remove(String series){
        Object tobeRemoved = null;
        for(Object object:((javafx.scene.chart.XYChart)body).getData()){
            javafx.scene.chart.XYChart.Series s = (javafx.scene.chart.XYChart.Series)object;
            if(s.getName().equals(series)){
                tobeRemoved = s;
            }
        }
        if(tobeRemoved!=null)
            ((javafx.scene.chart.XYChart)body).getData().remove(tobeRemoved);
    }
}
