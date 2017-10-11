package com.whitewoodcity;

import com.whitewoodcity.ui.PagePane;
import com.whitewoodcity.util.Res;
import io.vertx.core.Vertx;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.script.ScriptEngineManager;

import java.util.*;

import static io.vertx.core.impl.FileResolver.CACHE_DIR_BASE_PROP_NAME;
import static io.vertx.core.spi.resolver.ResolverProvider.DISABLE_DNS_RESOLVER_PROP_NAME;

public class Main extends Application {

    public static Vertx vertx;
    public static ScriptEngineManager scriptEngineManager;
    public static Map<String, StringProperty> namesMap = new HashMap<>();

    private PagePane tabPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.titleProperty().bind(namesMap.get("title"));
        primaryStage.getIcons().add(new Image("icons/xbrowser.png"));
        Group root = new Group();
        Button button = new Button("+");
        tabPane = new PagePane();

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
        tabPane.close();
        super.stop();
    }

    public static void main(String[] args) throws Exception{

        System.getProperties().setProperty(DISABLE_DNS_RESOLVER_PROP_NAME,"true");
        System.getProperties().setProperty(CACHE_DIR_BASE_PROP_NAME, Res.getDefaultDirectory().getPath());
        vertx = Vertx.vertx();
        scriptEngineManager = new ScriptEngineManager();
        generateNames(Locale.getDefault());

        launch(args);
    }

    public static void generateNames(Locale locale){

        ResourceBundle resourceBundle = ResourceBundle.getBundle("properties/names", locale);

        Enumeration<String> keys = resourceBundle.getKeys();

        while(keys.hasMoreElements()){
            String name = keys.nextElement();
            if(namesMap.get(name)==null){
                namesMap.put(name,new SimpleStringProperty(resourceBundle.getString(name)));
            }else{
                namesMap.get(name).set(resourceBundle.getString(name));
            }
        }

    }
}
