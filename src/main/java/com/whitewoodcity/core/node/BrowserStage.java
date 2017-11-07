package com.whitewoodcity.core.node;

import com.whitewoodcity.Main;
import com.whitewoodcity.ui.PagePane;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BrowserStage extends Stage{
    Group root = new Group();
    Button button = new Button("+");
    PagePane tabPane = new PagePane();

    Scene scene;

    public BrowserStage() {

        scene = new Scene(root, 960, 540);
        String css = getClass().getResource("/css/browser.css").toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);

        root.getChildren().addAll(tabPane,button);

        tabPane.prefWidthProperty().bind(scene.widthProperty());
        tabPane.prefHeightProperty().bind(scene.heightProperty());
        tabPane.setTabMaxWidth(100);
        tabPane.setTabMinWidth(100);

//        tabPane.buildPane();

        this.setScene(scene);

        display();

        tabPane.prefWidthProperty().bind(scene.widthProperty());
        StackPane headerArea = (StackPane) tabPane.lookup(".tab-header-area");
//        StackPane backPane= (StackPane) tabPane.lookup(".tab-header-background");
//        backPane.setStyle("-fx-background-color:#F4F4F4");
        Insets insets = headerArea.getInsets();
        insets = new Insets(insets.getTop(),insets.getRight(),insets.getBottom(),insets.getLeft()+button.getWidth());
        headerArea.setPadding(insets);

        button.setLayoutX(insets.getLeft()-button.getWidth());
        button.setLayoutY(insets.getTop());
        button.setOnAction(event -> {
            tabPane.buildPane();
        });

        this.setResizable(true);
    }

    public void display(){
        super.show();
        tabPane.buildPane();
    }

    public void close(){
        tabPane.close();
        super.close();
    }
}
