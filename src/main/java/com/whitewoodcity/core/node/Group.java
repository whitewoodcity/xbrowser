package com.whitewoodcity.core.node;


import javafx.scene.shape.Rectangle;

public class Group implements Node{

    private javafx.scene.Group body;
    private Rectangle background;

    public Group(){
        body = new javafx.scene.Group();

        background = new Rectangle();
        body.getChildren().add(body);
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
}
