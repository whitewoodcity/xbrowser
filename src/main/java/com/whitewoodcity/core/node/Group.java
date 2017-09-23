package com.whitewoodcity.core.node;


import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Rectangle;

public class Group implements Node{

    private javafx.scene.Group body;
    private Rectangle background;

    public Group(){
        body = new javafx.scene.Group();

        background = new Rectangle();
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
