package com.whitewoodcity.core.node.layout;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class FrameLayout extends ViewGroup{

    @Override
    public Pane getPane() {
        Pane pane=new StackPane();
        pane.setStyle("-fx-background-color:red");
        return pane;
    }

    @Override
    public Node getNode() {
        return pane;
    }


}
