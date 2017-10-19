package com.whitewoodcity.core.node.view;

import com.whitewoodcity.core.node.Node;
import com.whitewoodcity.core.node.conrol.Control;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ImageView implements Node {

    javafx.scene.image.ImageView body;

    public ImageView() {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.image.ImageView();
        else Platform.runLater(() -> body = new javafx.scene.image.ImageView());
    }

    public ImageView(String url) {
        if (url == null) {
            if (Platform.isFxApplicationThread()) body = new javafx.scene.image.ImageView();
            else Platform.runLater(() -> body = new javafx.scene.image.ImageView());
        } else {
            if (Platform.isFxApplicationThread()) body = new javafx.scene.image.ImageView(url);
            else Platform.runLater(() -> body = new javafx.scene.image.ImageView(url));
        }
    }

    public void setX(double x) {
        if (Platform.isFxApplicationThread()) {
            body.setLayoutX(x);
        } else Platform.runLater(() -> body.setLayoutX(x));
    }

    public void setY(double y) {
        if (Platform.isFxApplicationThread()) {
            body.setLayoutY(y);
        } else Platform.runLater(() -> body.setLayoutY(y));
    }

    public double getX() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return body.getLayoutX();
        }
        final FutureTask<Double> task = new FutureTask<>(() -> body.getLayoutX());
        Platform.runLater(task);
        return task.get();


    }

    public double getY() throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return body.getLayoutY();
        }
        final FutureTask<Double> task = new FutureTask<>(() -> body.getLayoutY());
        Platform.runLater(task);
        return task.get();

    }

    public DoubleProperty xProperty() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return body.layoutXProperty();
        }
        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> body.layoutXProperty());
        Platform.runLater(task);
        return task.get();


    }

    public DoubleProperty yProperty() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return body.layoutYProperty();
        }
        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> body.layoutYProperty());
        Platform.runLater(task);
        return task.get();


    }

    public DoubleProperty widthProperty() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return body.fitWidthProperty();
        }
        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> body.fitWidthProperty());
        Platform.runLater(task);
        return task.get();


    }

    public DoubleProperty heightProperty() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return body.fitHeightProperty();
        }
        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> body.fitHeightProperty());
        Platform.runLater(task);
        return task.get();

    }

    public void setWidth(double width) {
        if (Platform.isFxApplicationThread()) {
            body.setFitWidth(width);
        } else Platform.runLater(() -> body.setFitWidth(width));
    }

    public double getWidth() throws InterruptedException, ExecutionException {


        if (Platform.isFxApplicationThread()) {
            return body.getFitWidth();
        }
        final FutureTask<Double> task = new FutureTask<>(() -> body.getFitWidth());
        Platform.runLater(task);
        return task.get();


    }

    public void setHeight(double height) {
        if (Platform.isFxApplicationThread()) {
            body.setFitHeight(height);
        } else Platform.runLater(() -> body.setFitHeight(height));
    }

    public double getHeight() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return body.getFitHeight();
        }
        final FutureTask<Double> task = new FutureTask<>(() -> body.getFitHeight());
        Platform.runLater(task);
        return task.get();
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void setImage(Object image) {
        if (image != null && image instanceof Image) {
            if (Platform.isFxApplicationThread()) {
                body.setImage((Image) image);
            } else Platform.runLater(() -> body.setImage((Image) image));
        }

    }
}
