package com.whitewoodcity.core.node.chart;

import javafx.scene.chart.*;

public class XYChart extends Chart{

    public XYChart(XYChartType type,AxisType xAxisType,AxisType yAxisType){

        final Axis xAxis;
        final Axis yAxis;

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
            case BAR:
                body = new BarChart(xAxis,yAxis);
                break;
            case AREA:
                body = new AreaChart(xAxis,yAxis);
                break;
            case BUBBLE:
                body = new BubbleChart(xAxis,yAxis);
                break;
            case SCATTER:
                body = new ScatterChart(xAxis,yAxis);
                break;
            default:
                body = new LineChart(xAxis,yAxis);
                break;
        }

    }


}
