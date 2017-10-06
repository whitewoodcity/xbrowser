package com.whitewoodcity.core.node.chart;

import com.whitewoodcity.core.node.Node;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Side;

public abstract class Chart implements Node {
    javafx.scene.chart.Chart body;

    public javafx.scene.Node getNode(){
        return body;
    }

    public void setX(double x) {
        body.setLayoutX(x);
    }

    public void setY(double y){
        body.setLayoutY(y);
    }

    public double getX(){
        return body.getLayoutX();
    }

    public double getY(){
        return body.getLayoutY();
    }

    public DoubleProperty xProperty(){
        return body.layoutXProperty();
    }

    public DoubleProperty yProperty(){
        return body.layoutYProperty();
    }

    public DoubleProperty x_property(){
        return xProperty();
    }

    public DoubleProperty y_property(){
        return yProperty();
    }

    public DoubleProperty xproperty(){
        return xProperty();
    }

    public DoubleProperty yproperty(){
        return yProperty();
    }

    public DoubleProperty widthProperty(){
        return body.prefWidthProperty();
    }

    public DoubleProperty heightProperty(){
        return body.prefHeightProperty();
    }

    public DoubleProperty width_property(){
        return widthProperty();
    }

    public DoubleProperty height_property(){
        return heightProperty();
    }

    public DoubleProperty widthproperty(){
        return widthProperty();
    }

    public DoubleProperty heightproperty(){
        return heightProperty();
    }

    public void setWidth(double width){
        body.setPrefWidth(width);
    }

    public double getWidth(){
        if(body.getWidth() > 0.000001) return body.getWidth();
        else return body.getPrefWidth();
    }

    public void setHeight(double height){
        body.setPrefHeight(height);
    }

    public double getHeight(){
        if(body.getHeight() > 0.000001) return body.getHeight();
        else return body.getPrefHeight();
    }

    public javafx.scene.chart.Chart getBody() {
        return body;
    }

    public void setTitle(String title){
        body.setTitle(title);
    }

    public String getTitle(){
        return body.getTitle();
    }

    public void setTitleSide(String side){
        side = side == null ? "":side;
        side = side.trim().toLowerCase();
        switch (side){
            case "bottom":
                body.setTitleSide(Side.BOTTOM);
                break;
            case "left":
                body.setTitleSide(Side.LEFT);
                break;
            case "right":
                body.setTitleSide(Side.RIGHT);
                break;
            default:
                body.setTitleSide(Side.TOP);
                break;
        }
    }

    public void setTitle_side(String side){
        setTitleSide(side);
    }

    public void setTitleside(String side){
        setTitleSide(side);
    }

    public void setLegendSide(String side){
        side = side == null ? "":side;
        side = side.trim().toLowerCase();
        switch (side){
            case "top":
                body.setLegendSide(Side.TOP);
                break;
            case "left":
                body.setLegendSide(Side.LEFT);
                break;
            case "right":
                body.setLegendSide(Side.RIGHT);
                break;
            default:
                body.setLegendSide(Side.BOTTOM);
                break;
        }
    }

    public void setLegend_side(String side){
        setLegendSide(side);
    }

    public void setLegendside(String side){
        setLegendSide(side);
    }
}
