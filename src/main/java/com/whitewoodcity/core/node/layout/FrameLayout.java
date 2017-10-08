package com.whitewoodcity.core.node.layout;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class FrameLayout extends ViewGroup{

    public FrameLayout() {
        pane=new StackPane();
    }

    @Override
    public Node getNode() {
        return pane;
    }


}
