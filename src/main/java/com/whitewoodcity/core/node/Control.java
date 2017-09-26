package com.whitewoodcity.core.node;

import javafx.beans.property.DoubleProperty;

public class Control implements Node{
    
    javafx.scene.control.Control body;

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

    public javafx.scene.control.Control getBody() {
        return body;
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

}
