package com.whitewoodcity.core.node.conrol;

import com.whitewoodcity.core.node.Node;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public abstract class Control implements Node {

    javafx.scene.control.Control body;

    StringProperty name = new SimpleStringProperty();

    public void setX(double x) {
        if(Platform.isFxApplicationThread()) body.setLayoutX(x);
        else Platform.runLater(()->body.setLayoutX(x));
    }

    public void setY(double y){
        if(Platform.isFxApplicationThread()) body.setLayoutY(y);
        else Platform.runLater(()->body.setLayoutY(y));
    }

    public double getX() throws InterruptedException,ExecutionException{
        if(Platform.isFxApplicationThread()) return body.getLayoutX();
        final FutureTask<Double> task = new FutureTask<>(()->body.getLayoutX());
        Platform.runLater(task);
        return task.get();
    }

    public double getY() throws InterruptedException,ExecutionException{
        if(Platform.isFxApplicationThread()) return body.getLayoutY();
        final FutureTask<Double> task = new FutureTask<>(()->body.getLayoutY());
        Platform.runLater(task);
        return task.get();
    }

    public DoubleProperty xProperty() throws InterruptedException,ExecutionException{
        if(Platform.isFxApplicationThread()) return body.layoutXProperty();
        final FutureTask<DoubleProperty> task = new FutureTask<>(()->body.layoutXProperty());
        Platform.runLater(task);
        return task.get();
    }

    public DoubleProperty yProperty() throws InterruptedException,ExecutionException{
        if(Platform.isFxApplicationThread()) return body.layoutYProperty();
        final FutureTask<DoubleProperty> task = new FutureTask<>(()->body.layoutYProperty());
        Platform.runLater(task);
        return task.get();
    }

    public DoubleProperty x_property()throws InterruptedException,ExecutionException{
        return xProperty();
    }

    public DoubleProperty y_property()throws InterruptedException,ExecutionException{
        return yProperty();
    }

    public DoubleProperty xproperty()throws InterruptedException,ExecutionException{
        return xProperty();
    }

    public DoubleProperty yproperty()throws InterruptedException,ExecutionException{
        return yProperty();
    }

    public DoubleProperty widthProperty()throws InterruptedException,ExecutionException{
        if(Platform.isFxApplicationThread()) return body.prefWidthProperty();
        final FutureTask<DoubleProperty> task = new FutureTask<>(()->body.prefWidthProperty());
        Platform.runLater(task);
        return task.get();
    }

    public DoubleProperty heightProperty()throws InterruptedException,ExecutionException{
        if(Platform.isFxApplicationThread()) return body.prefHeightProperty();
        final FutureTask<DoubleProperty> task = new FutureTask<>(()->body.prefHeightProperty());
        Platform.runLater(task);
        return task.get();
    }

    public DoubleProperty width_property()throws InterruptedException,ExecutionException{
        return widthProperty();
    }

    public DoubleProperty height_property()throws InterruptedException,ExecutionException{
        return heightProperty();
    }

    public DoubleProperty widthproperty()throws InterruptedException,ExecutionException{
        return widthProperty();
    }

    public DoubleProperty heightproperty()throws InterruptedException,ExecutionException{
        return heightProperty();
    }

    public void setWidth(double width){
        if(Platform.isFxApplicationThread()) body.setPrefWidth(width);
        else Platform.runLater(()->body.setPrefWidth(width));
    }

    public double getWidth() throws InterruptedException,ExecutionException{

        if(Platform.isFxApplicationThread()){
            return getWidthBase();
        }
        final FutureTask<Double> task = new FutureTask<>(this::getWidthBase);
        Platform.runLater(task);
        return task.get();

//        if(body.getWidth() > 0.000001) return body.getWidth();
//        else return body.getPrefWidth();
    }

    private double getWidthBase(){
        if(body.getWidth() > 0.000001) return body.getWidth();
        else return body.getPrefWidth();
    }

    public void setHeight(double height){
        if(Platform.isFxApplicationThread()) body.setPrefHeight(height);
        else Platform.runLater(()->body.setPrefHeight(height));
    }

    public double getHeight() throws InterruptedException,ExecutionException{
        if(Platform.isFxApplicationThread()){
            return getHeightBase();
        }
        final FutureTask<Double> task = new FutureTask<>(this::getHeightBase);
        Platform.runLater(task);
        return task.get();
//        if(body.getHeight() > 0.000001) return body.getHeight();
//        else return body.getPrefHeight();
    }

    private double getHeightBase(){
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty name_property() {
        return name;
    }

    public StringProperty nameproperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Object getValue() throws InterruptedException,ExecutionException{
        return name.getValue();
    }
}
