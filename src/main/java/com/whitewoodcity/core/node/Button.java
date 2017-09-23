package com.whitewoodcity.core.node;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class Button extends javafx.scene.control.Button{

    public Button() {
        super();
    }

    public Button(String text) {
        super(text);
    }

    public Button(String text, Node graphic) {
        super(text, graphic);
    }

    public void setX(double x) {
        setLayoutX(x);
    }

    public void setY(double y){
        setLayoutY(y);
    }

    public double getX(){
        return getLayoutX();
    }

    public double getY(){

        return getLayoutY();
    }

    public DoubleProperty xProperty(){
        return layoutXProperty();
    }

    public DoubleProperty yProperty(){
        return layoutYProperty();
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
        return widthProperty();
    }

    public ReadOnlyDoubleProperty height_property(){
        return heightProperty();
    }

    public ReadOnlyDoubleProperty widthproperty(){
        return widthProperty();
    }

    public ReadOnlyDoubleProperty heightproperty(){
        return heightProperty();
    }

    public void setW(double w){
        setPrefWidth(w);
    }

    public double getW(){
        return getWidth();
    }
}
