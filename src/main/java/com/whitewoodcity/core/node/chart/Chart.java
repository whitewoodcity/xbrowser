package com.whitewoodcity.core.node.chart;

import com.whitewoodcity.core.node.Node;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Side;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public abstract class Chart implements Node {
    javafx.scene.chart.Chart body;

    public javafx.scene.Node getNode(){
        return body;
    }

    public void setX(double x) {
        if(Platform.isFxApplicationThread()) body.setLayoutX(x);
        else Platform.runLater(()->body.setLayoutX(x));
    }

    public void setY(double y){
        if(Platform.isFxApplicationThread()) body.setLayoutY(y);
        else Platform.runLater(()->body.setLayoutY(y));
    }

    public double getX() throws InterruptedException,ExecutionException {
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
    }

    private double getHeightBase(){
        if(body.getHeight() > 0.000001) return body.getHeight();
        else return body.getPrefHeight();
    }

    public javafx.scene.chart.Chart getBody() {
        return body;
    }

    public void setTitle(String title){
        if(Platform.isFxApplicationThread()) body.setTitle(title);
        else Platform.runLater(()->body.setTitle(title));
    }

    public String getTitle() throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()){
            return body.getTitle();
        }
        final FutureTask<String> task = new FutureTask<>(()->body.getTitle());
        Platform.runLater(task);
        return task.get();
    }

    public StringProperty titleProperty()throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()){
            return body.titleProperty();
        }
        final FutureTask<StringProperty> task = new FutureTask<>(()-> body.titleProperty());
        Platform.runLater(task);
        return task.get();

    }

    public StringProperty title_property()throws InterruptedException, ExecutionException{
        return titleProperty();
    }

    public StringProperty titleproperty()throws InterruptedException, ExecutionException{
        return titleProperty();
    }

    public void setTitleSide(String side){
        if(Platform.isFxApplicationThread()) setTitleSideBase(side);
        else Platform.runLater(()->setTitleSideBase(side));
    }
    private void setTitleSideBase(String side){
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
        if(Platform.isFxApplicationThread()) setLegendSideBase(side);
        else Platform.runLater(()->setLegendSideBase(side));
    }

    private void setLegendSideBase(String side){
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
