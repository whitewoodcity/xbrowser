package com.whitewoodcity.core.node.view;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MediaView extends View {
    javafx.scene.media.MediaView body;

    public MediaView() {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.media.MediaView();
        else Platform.runLater(() -> body = new javafx.scene.media.MediaView());
    }

    public MediaView(String url) {
        if (url == null) {
            if (Platform.isFxApplicationThread()) body = new javafx.scene.media.MediaView();
            else Platform.runLater(() -> body = new javafx.scene.media.MediaView());
        } else {
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(url));
            mediaPlayer.setAutoPlay(true);
            if (Platform.isFxApplicationThread())
                body = new javafx.scene.media.MediaView(mediaPlayer);
            else Platform.runLater(()-> body = new javafx.scene.media.MediaView(mediaPlayer));
        }
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

    public void setMedia(Object media) {
        if (media != null && media instanceof Media) {
            if (Platform.isFxApplicationThread()) {
                body.setMediaPlayer(new MediaPlayer((Media) media));
            } else Platform.runLater(() -> body.setMediaPlayer(new MediaPlayer((Media) media)));
        }
    }
}
