package com.whitewoodcity.core.node.view;

import com.whitewoodcity.core.node.Node;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public abstract class View implements Node {

    public void setX(double x) {
        if (Platform.isFxApplicationThread()) {
            getNode().setLayoutX(x);
        } else Platform.runLater(() -> getNode().setLayoutX(x));
    }

    public void setY(double y) {
        if (Platform.isFxApplicationThread()) {
            getNode().setLayoutY(y);
        } else Platform.runLater(() -> getNode().setLayoutY(y));
    }

    public double getX() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return getNode().getLayoutX();
        }
        final FutureTask<Double> task = new FutureTask<>(() -> getNode().getLayoutX());
        Platform.runLater(task);
        return task.get();

    }

    public double getY() throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return getNode().getLayoutY();
        }
        final FutureTask<Double> task = new FutureTask<>(() -> getNode().getLayoutY());
        Platform.runLater(task);
        return task.get();

    }

    public DoubleProperty xProperty() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return getNode().layoutXProperty();
        }
        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> getNode().layoutXProperty());
        Platform.runLater(task);
        return task.get();


    }

    public DoubleProperty yProperty() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return getNode().layoutYProperty();
        }
        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> getNode().layoutYProperty());
        Platform.runLater(task);
        return task.get();


    }

    public abstract DoubleProperty widthProperty() throws InterruptedException, ExecutionException ;

    public abstract DoubleProperty heightProperty() throws InterruptedException, ExecutionException;

    public abstract void setWidth(double width);

    public  abstract double getWidth() throws InterruptedException, ExecutionException;

    public abstract void setHeight(double height);

    public abstract double getHeight() throws InterruptedException, ExecutionException;
}
