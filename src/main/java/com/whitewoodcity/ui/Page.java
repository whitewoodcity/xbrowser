package com.whitewoodcity.ui;

import com.whitewoodcity.controller.TabContent;
import com.whitewoodcity.core.parse.PageParser;
import com.whitewoodcity.util.Res;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * 选项卡界面
 */
public class Page extends Tab{

    private PageParser parser;
    private Tooltip tooltip;

    public Page(String title) {
        parser=new PageParser();
        setText(title);
        setClosable(true);
        tooltip=new Tooltip();
        tooltip.setText(title);
        setTooltip(tooltip);
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(Res.getFxmlRes("tab_content"));
            Parent parent=fxmlLoader.load();
            setContent(parent);
            TabContent controller=fxmlLoader.getController();
            controller.setTab(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
