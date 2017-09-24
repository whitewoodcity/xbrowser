package com.whitewoodcity.core.node;

public interface Node {
    javafx.scene.Node getNode();

    default void setDisable(boolean value){
        getNode().setDisable(value);
    }

    default boolean isDisable(){
        return getNode().isDisable();
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
}
