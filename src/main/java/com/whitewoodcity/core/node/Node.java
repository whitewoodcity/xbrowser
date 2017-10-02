package com.whitewoodcity.core.node;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;

public interface Node {
    javafx.scene.Node getNode();

    default void setDisable(boolean value){
        getNode().setDisable(value);
    }

    default boolean isDisable(){
        return getNode().isDisable();
    }

    default BooleanProperty disableProperty(){
        return getNode().disableProperty();
    }

    default BooleanProperty disable_property(){
        return disableProperty();
    }

    default BooleanProperty disableproperty(){
        return disableProperty();
    }

    default void setId(String id){
        getNode().setId(id);
    }

    default String getId(){
        return getNode().getId();
    }

    default void setRotate(double value){
        getNode().setRotate(value);
    }

    default double getRotate(){
        return getNode().getRotate();
    }

    default DoubleProperty rotateProperty(){
        return getNode().rotateProperty();
    }

    default DoubleProperty rotate_property(){
        return rotateProperty();
    }

    default DoubleProperty rotateproperty(){
        return rotateProperty();
    }

    default void setVisible(boolean visible){
        getNode().setVisible(visible);
    }

    default boolean isVisible(){
        return getNode().isVisible();
    }

    default BooleanProperty visibleProperty(){
        return getNode().visibleProperty();
    }

    default BooleanProperty visible_property(){
        return visibleProperty();
    }

    default BooleanProperty visibleproperty(){
        return visibleProperty();
    }

    default void setOpacity(double opacity){
        getNode().setOpacity(opacity);
    }

    default double getOpacity(){
        return getNode().getOpacity();
    }

    default DoubleProperty opacityProperty(){
        return getNode().opacityProperty();
    }

    default DoubleProperty opacity_property(){
        return opacityProperty();
    }

    default DoubleProperty opacityproperty(){
        return opacityProperty();
    }
}
