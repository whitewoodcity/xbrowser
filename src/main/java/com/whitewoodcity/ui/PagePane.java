package com.whitewoodcity.ui;

import com.whitewoodcity.Main;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

public class PagePane extends TabPane{

    public void buildPane(){
        Page page = new Page(Main.namesMap.get("tab").get(), this);
        getTabs().add(page);
        getSelectionModel().select(page);
    }

    public void close(){
        for(Tab tab:getTabs()){
            Page page = (Page)tab;
            if(page!=null){
                page.getOnClosed().handle(null);
            }
        }
    }
}
