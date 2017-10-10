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
            }else if(event.isShortcutDown()&&event.getCode()== KeyCode.W){
                Tab tab = this.getSelectionModel().getSelectedItem();
                if(tab!=null){
                    Page page = (Page)tab;
                    page.getOnClosed().handle(null);
                    this.getTabs().remove(this.getSelectionModel().getSelectedItem());
                }
            }else if(event.isShortcutDown()&&event.getCode()== KeyCode.S){
                Page page = (Page)this.getSelectionModel().getSelectedItem();
                page.getController().saveFile(null);
            }else if(event.isShortcutDown()&&event.getCode()== KeyCode.L){
                Page page = (Page)this.getSelectionModel().getSelectedItem();
                page.getController().onFileSelector(null);
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
