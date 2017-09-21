package com.whitewoodcity;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        ObjectMapper mapper = new ObjectMapper();

        Group root = new Group();
        Button button = new Button("+");
        TabPane tabPane = new TabPane();

        primaryStage.setTitle("XBrowser");
        Scene scene = new Scene(root, 960, 540);
        root.getChildren().addAll(tabPane,button);
        primaryStage.setScene(scene);
//        scene.getStylesheets().add(ClassLoader.getSystemResource("css/main.css").toExternalForm());
        primaryStage.show();

        Tab tab0 = new Tab("New Tab");
        tab0.setClosable(false);
        tabPane.getTabs().add(tab0);

        tabPane.prefWidthProperty().bind(scene.widthProperty());
        StackPane headerArea = (StackPane) tabPane.lookup(".tab-header-area");

        Insets insets = headerArea.getInsets();
        insets = new Insets(insets.getTop(),insets.getRight(),insets.getBottom(),insets.getLeft()+button.getWidth());
        headerArea.setPadding(insets);

        button.setLayoutX(insets.getLeft()-button.getWidth());
        button.setLayoutY(insets.getTop());


    }

    public static void main(String[] args) {
        launch(args);
    }
}
