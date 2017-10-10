package com.whitewoodcity.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;

public class PagePane extends TabPane{
    public PagePane(){
        super();

        this.setOnKeyPressed(event ->{
            if(event.isShortcutDown()&&event.getCode()== KeyCode.T){
                this.buildPane();
            }else if(event.isShortcutDown()){
                Tab tab = this.getSelectionModel().getSelectedItem();
                if(tab == null) return;
                Page page = (Page)tab;
                switch (event.getCode()){
                    case W:
                        page.getOnClosed().handle(null);
                        this.getTabs().remove(this.getSelectionModel().getSelectedItem());
                        break;
                    case S:
                        page.getController().saveFile(null);
                        break;
                    case L:
                        page.getController().onFileSelector(null);
                        break;
                }
            }
        });
    }

    public void buildPane(){
        getTabs().add(new Page("New Tab", this));
    }

    public void close(){
        for(Tab tab:getTabs()){
            Page page = (Page)tab;
            page.getOnClosed().handle(null);
        }
    }
}
