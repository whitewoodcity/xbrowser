package com.whitewoodcity.core.node;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public interface Node {
    javafx.scene.Node getNode();

    default void setDisable(boolean value){
        if(Platform.isFxApplicationThread()) getNode().setDisable(value);
        else Platform.runLater(()->getNode().setDisable(value));
    }

    default boolean isDisable() throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().isDisable();
        final FutureTask<Boolean> task = new FutureTask<>(()->getNode().isDisable());
        Platform.runLater(task);
        return task.get();
    }

    default BooleanProperty disableProperty() throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().disableProperty();
        final FutureTask<BooleanProperty> task = new FutureTask<>(()->getNode().disableProperty());
        Platform.runLater(task);
        return task.get();
    }

    default BooleanProperty disable_property() throws InterruptedException, ExecutionException{
        return disableProperty();
    }

    default BooleanProperty disableproperty() throws InterruptedException, ExecutionException{
        return disableProperty();
    }

    default void setId(String id){
        if(Platform.isFxApplicationThread()) getNode().setId(id);
        else Platform.runLater(()->getNode().setId(id));
    }

    default String getId() throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().getId();
        final FutureTask<String> task = new FutureTask<>(()->getNode().getId());
        Platform.runLater(task);
        return task.get();
    }

    default void setRotate(double value){
        if(Platform.isFxApplicationThread()) getNode().setRotate(value);
        else Platform.runLater(()->getNode().setRotate(value));
    }

    default double getRotate() throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().getRotate();
        final FutureTask<Double> task = new FutureTask<>(()->getNode().getRotate());
        Platform.runLater(task);
        return task.get();
    }

    default DoubleProperty rotateProperty()throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().rotateProperty();
        final FutureTask<DoubleProperty> task = new FutureTask<>(()->getNode().rotateProperty());
        Platform.runLater(task);
        return task.get();
    }

    default DoubleProperty rotate_property()throws InterruptedException, ExecutionException{
        return rotateProperty();
    }

    default DoubleProperty rotateproperty()throws InterruptedException, ExecutionException{
        return rotateProperty();
    }

    default void setVisible(boolean visible){
        if(Platform.isFxApplicationThread()) getNode().setVisible(visible);
        else Platform.runLater(()->getNode().setVisible(visible));
    }

    default boolean isVisible() throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().isVisible();
        final FutureTask<Boolean> task = new FutureTask<>(()->getNode().isVisible());
        Platform.runLater(task);
        return task.get();
    }

    default BooleanProperty visibleProperty()throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().visibleProperty();
        final FutureTask<BooleanProperty> task = new FutureTask<>(()->getNode().visibleProperty());
        Platform.runLater(task);
        return task.get();
    }

    default BooleanProperty visible_property()throws InterruptedException, ExecutionException{
        return visibleProperty();
    }

    default BooleanProperty visibleproperty()throws InterruptedException, ExecutionException{
        return visibleProperty();
    }

    default void setOpacity(double opacity){
        if(Platform.isFxApplicationThread()) getNode().setOpacity(opacity);
        else Platform.runLater(()->getNode().setOpacity(opacity));
    }

    default double getOpacity() throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().getOpacity();
        final FutureTask<Double> task = new FutureTask<>(()->getNode().getOpacity());
        Platform.runLater(task);
        return task.get();
    }

    default DoubleProperty opacityProperty()throws InterruptedException, ExecutionException{
        if(Platform.isFxApplicationThread()) return getNode().opacityProperty();
        final FutureTask<DoubleProperty> task = new FutureTask<>(()->getNode().opacityProperty());
        Platform.runLater(task);
        return task.get();
    }

    default DoubleProperty opacity_property()throws InterruptedException, ExecutionException{
        return opacityProperty();
    }

    default DoubleProperty opacityproperty()throws InterruptedException, ExecutionException{
        return opacityProperty();
    }
}
