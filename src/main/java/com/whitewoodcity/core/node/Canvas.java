package com.whitewoodcity.core.node;

import javafx.beans.property.DoubleProperty;

public class Canvas implements Node{
    private javafx.scene.canvas.Canvas body;

    public Canvas(){
        body= new javafx.scene.canvas.Canvas();
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void setWidth(double width){
        body.setWidth(width);
    }

    public double getWidth(){
        if(body.getWidth() > 0.000001) return body.getWidth();
        else return body.getWidth();
    }

    public void setHeight(double height){
        body.setHeight(height);
    }

    public double getHeight(){
        if(body.getHeight() > 0.000001) return body.getHeight();
        else return body.getHeight();
    }

    public DoubleProperty widthProperty(){
        return body.widthProperty();
    }

    public DoubleProperty heightProperty(){
        return body.heightProperty();
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

}
