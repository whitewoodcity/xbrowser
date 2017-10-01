package com.whitewoodcity.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PagePane extends TabPane{
    public void buildPane(){
        getTabs().add(new Page("新标签页", this));
    }

    public void close(){
        for(Tab tab:getTabs()){
            Page page = (Page)tab;
            page.getOnClosed().handle(null);
        }
    }
}
