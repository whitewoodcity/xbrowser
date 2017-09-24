package com.whitewoodcity.core.node;


import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Group implements Node{

    private Pane body;
    private Rectangle background;

    public Group(){
        body = new Pane();

        background = new Rectangle();
        background.setWidth(1000);
        background.setHeight(1000);
        body.getChildren().add(background);
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void add(Node... nodes){
        for(Node node:nodes){
            body.getChildren().add(node.getNode());
        }
    }

    public void clear(){
        body.getChildren().clear();
        body.getChildren().add(background);
    }

    public DoubleProperty widthProperty(){
        return background.widthProperty();
    }

    public DoubleProperty heightProperty(){
        return background.heightProperty();
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
        background.setWidth(width);
    }

    public double getWidth(){
        return background.getWidth();
    }

    public void setHeight(double height){
        background.setHeight(height);
    }

    public double getHeight(){
        return background.getHeight();
    }
}
