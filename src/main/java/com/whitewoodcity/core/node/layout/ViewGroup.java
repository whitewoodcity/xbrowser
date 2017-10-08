package com.whitewoodcity.core.node.layout;

import com.whitewoodcity.core.node.Node;
import javafx.scene.layout.Pane;

public abstract class ViewGroup implements Node{

    Pane pane;


    public void addNode(Node node){
        if(pane!=null){
            pane.getChildren().add(node.getNode());
        }
    }
    public void addNodes(Node... nodes){
        if(pane!=null){
            for (Node node:nodes)
                pane.getChildren().add(node.getNode());
        }
    }
}
