package com.whitewoodcity.ui;

import com.whitewoodcity.controller.TabContent;
import com.whitewoodcity.util.Res;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

/**
 * 选项卡界面
 */
public class Page extends Tab{

    private Tooltip tooltip;
    private TabContent controller;

    public Page(String title, TabPane tabPane) {
        setText(title);
        setClosable(true);
        tooltip=new Tooltip();
        tooltip.setText(title);
        setTooltip(tooltip);
        try {
            StackPane headerArea = (StackPane) tabPane.lookup(".tab-header-area");

            URL location = getClass().getResource("/fxml/tab_content.fxml");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent parent =  fxmlLoader.load(location.openStream());

//            FXMLLoader fxmlLoader=new FXMLLoader(Res.getFxmlRes("tab_content"));
//            Pane parent=fxmlLoader.load();
            setContent(parent);
            controller=fxmlLoader.getController();
            controller.getHeader().prefWidthProperty().bind(tabPane.widthProperty());
            controller.getContainer().prefWidthProperty().bind(tabPane.widthProperty());
            controller.getContainer().prefHeightProperty().bind(tabPane.heightProperty()
                    .subtract(headerArea.heightProperty())
                    .subtract(controller.getHeader().heightProperty()));
            this.setOnClosed((event)->controller.close());
            controller.setTab(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TabContent getController() {
        return controller;
    }
}
