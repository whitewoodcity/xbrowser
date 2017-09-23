package com.whitewoodcity.ui;

import com.whitewoodcity.controller.TabContent;
import com.whitewoodcity.util.Res;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;

import java.io.IOException;

/**
 * 选项卡界面
 */
public class Page extends Tab{

    private Tooltip tooltip;

    public Page(String title, TabPane tabPane) {
        setText(title);
        setClosable(true);
        tooltip=new Tooltip();
        tooltip.setText(title);
        setTooltip(tooltip);
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(Res.getFxmlRes("tab_content"));
            Group parent=fxmlLoader.load();
            setContent(parent);
            TabContent controller=fxmlLoader.getController();
            controller.getHeader().prefWidthProperty().bind(tabPane.widthProperty());
            controller.setTab(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
