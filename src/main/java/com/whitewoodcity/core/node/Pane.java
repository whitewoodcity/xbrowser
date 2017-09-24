package com.whitewoodcity.core.node;

public class Pane implements Node{

    private javafx.scene.layout.Pane body;

    public Pane(){
        body = new javafx.scene.layout.Pane();
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void add(Node... nodes){
        for(Node node:nodes){
            body.getChildren().add(node.getNode());
        }
    }

    public void clear(){
        body.getChildren().clear();
    }
}
