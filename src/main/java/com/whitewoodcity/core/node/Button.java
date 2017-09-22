package com.whitewoodcity.core.node;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;

public class Button extends javafx.scene.control.Button{
    public DoubleProperty x = new SimpleDoubleProperty();
    public DoubleProperty y = new SimpleDoubleProperty();

    public Button(String text){
        super(text);
        layoutXProperty().bind(x);
        layoutYProperty().bind(y);
    }

    public Button(String text, Node graphic){
        super(text, graphic);
        layoutXProperty().bind(x);
        layoutYProperty().bind(y);
    }
}
