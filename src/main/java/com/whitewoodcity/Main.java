package com.whitewoodcity;

import com.whitewoodcity.ui.PagePane;
import io.vertx.core.Vertx;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Vertx vertx;

    public static Stage main;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("XBrowser");
        main=primaryStage;
        Group root = new Group();
        Button button = new Button("+");
        PagePane tabPane = new PagePane();

        Scene scene = new Scene(root, 960, 540);
        root.getChildren().addAll(tabPane,button);
        primaryStage.setScene(scene);
//        scene.getStylesheets().add(ClassLoader.getSystemResource("css/main.css").toExternalForm());
        primaryStage.show();

        tabPane.prefWidthProperty().bind(scene.widthProperty());
        tabPane.prefHeightProperty().bind(scene.heightProperty());
        tabPane.setTabMaxWidth(100);
        tabPane.setTabMinWidth(100);

        tabPane.buildPane();

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
    }

    @Override
    public void stop() throws Exception {
        vertx.close();
        super.stop();
    }


    public static void main(String[] args) {
        System.getProperties().setProperty("vertx.disableDnsResolver","true");
        vertx = Vertx.vertx();
        launch(args);
    }
}
