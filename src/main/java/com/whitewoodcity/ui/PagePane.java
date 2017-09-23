package com.whitewoodcity.ui;

import javafx.scene.control.TabPane;

public class PagePane extends TabPane{
    public void buildPane(){
        getTabs().add(new Page("新标签页", this));
    }
}
