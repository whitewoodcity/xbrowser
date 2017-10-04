package com.whitewoodcity.core.node.conrol;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;

public class ImageView extends Control{

    javafx.scene.image.ImageView body;

    public ImageView() {
        body = new javafx.scene.image.ImageView();
    }

    public ImageView(String url) {
        if(url == null){
            body = new javafx.scene.image.ImageView();
        }else {
            body = new javafx.scene.image.ImageView(url);
        }
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

    public DoubleProperty widthProperty(){
        return body.fitWidthProperty();
    }

    public DoubleProperty heightProperty(){
        return body.fitHeightProperty();
    }

    public void setWidth(double width){
        body.setFitWidth(width);
    }

    public double getWidth(){
        return body.getFitWidth();
    }

    public void setHeight(double height){
        body.setFitHeight(height);
    }

    public double getHeight(){
        return body.getFitHeight();
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void setImage(Object image){
        if(image!=null&&image instanceof Image)
        body.setImage((Image)image);
    }
}
