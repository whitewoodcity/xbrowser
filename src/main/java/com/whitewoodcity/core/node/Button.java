package com.whitewoodcity.core.node;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Button implements Node{

    private javafx.scene.control.Button body;
    private StringProperty text;

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

    public javafx.scene.control.Button getBody() {
        return body;
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void setAction(EventHandler<ActionEvent> value){
        body.setOnAction(value);
    }

    public void setaction(EventHandler<ActionEvent> value){
        setAction(value);
    }

    public void set_action(EventHandler<ActionEvent> value){
        setAction(value);
    }

    public String getText() {
        return body.getText();
    }

    public StringProperty textProperty() {
        return body.textProperty();
    }

    public void setText(String text) {
        body.setText(text);
    }

    public StringProperty text_property() {
        return textProperty();
    }

    public StringProperty textproperty() {
        return textProperty();
    }
}
