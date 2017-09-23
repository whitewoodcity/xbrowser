package com.whitewoodcity.core.node;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;

public class Button implements Node{

    private javafx.scene.control.Button body;

    public Button() {
        body = new javafx.scene.control.Button();
    }

    public Button(String text) {
        body = new javafx.scene.control.Button(text);
    }

    public Button(String text, javafx.scene.Node graphic) {
        body = new javafx.scene.control.Button(text, graphic);
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

    public ReadOnlyDoubleProperty width_property(){
        return body.widthProperty();
    }

    public ReadOnlyDoubleProperty height_property(){
        return body.heightProperty();
    }

    public ReadOnlyDoubleProperty widthproperty(){
        return body.widthProperty();
    }

    public ReadOnlyDoubleProperty heightproperty(){
        return body.heightProperty();
    }

    public void setWidth(int width){
        body.setPrefWidth(width);
    }

    public double getWidth(){
        return body.getPrefWidth();
    }

    public javafx.scene.control.Button getBody() {
        return body;
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }
}
