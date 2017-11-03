package com.whitewoodcity;

import com.whitewoodcity.core.node.BrowserStage;
import com.whitewoodcity.security.ApplicationSecurityManager;
import com.whitewoodcity.thread.CustomerThread;
import com.whitewoodcity.util.Res;
import com.whitewoodcity.verticle.DatagramVerticle;
import com.whitewoodcity.verticle.WebClientVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.script.ScriptEngineManager;

import java.util.*;

import static io.vertx.core.impl.FileResolver.CACHE_DIR_BASE_PROP_NAME;
import static io.vertx.core.spi.resolver.ResolverProvider.DISABLE_DNS_RESOLVER_PROP_NAME;

public class Main extends Application {

    public static final String DEFAULT_MAX_WORKER_EXECUTE_TIME = "defaultMaxExecutionTime";
    public static final String DEFAULT_TOLERATED_WORKER_EXECUTE_TIME = "defaultToleratedExecutionTime";
    private static final String GLOBAL_ACCESS_CODE = UUID.randomUUID().toString();

    public static Vertx vertx;
    public static ScriptEngineManager scriptEngineManager;
    public static Map<String, StringProperty> namesMap = new HashMap<>();
    public static Map<String, Buffer> bufferMap = new HashMap<>();

    private BrowserStage browser;

    @Override
    public void start(Stage primaryStage) throws Exception{
        displayBrowser();

//        HBox root = new HBox();
//        Scene scene = new Scene(root);
//        Button button0 = new Button();
//        Button button1 = new Button();
//
//        button0.setOnAction(event -> displayBrowser());
//        button1.setOnAction(event -> closeBrowser());
//
//        button0.setText("strt");
//        button1.setText("clse");
//
//        root.getChildren().addAll(button0,button1);
//
//        primaryStage.setScene(scene);
//        primaryStage.setAlwaysOnTop(true);
//
//        primaryStage.show();
//        primaryStage.setOnCloseRequest(e ->stop());
    }

    public void displayBrowser(){
        if(browser==null){
            browser = new BrowserStage();

            browser.setOnCloseRequest(e -> {
                browser.close();
                stop();
            });

            browser.titleProperty().unbind();
            browser.titleProperty().bind(namesMap.get("title"));
            browser.getIcons().add(new Image("icons/xbrowser.png"));
        }else{
            browser.display();
        }
    }

    public void closeBrowser(){
        if(browser!=null) browser.close();
    }

    @Override
    public void stop(){
        closeBrowser();
        exit();
    }

    private void exit(){
        vertx.close();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) throws Exception{

        System.getProperties().setProperty(DISABLE_DNS_RESOLVER_PROP_NAME,"true");
        System.getProperties().setProperty(CACHE_DIR_BASE_PROP_NAME, Res.getDefaultDirectory().getPath());
        VertxOptions vertxOptions = new VertxOptions().setMaxWorkerExecuteTime(10_000_000_000L);
        System.getProperties().setProperty(DEFAULT_TOLERATED_WORKER_EXECUTE_TIME,vertxOptions.getMaxWorkerExecuteTime()/1000000+"");
        System.getProperties().setProperty(DEFAULT_MAX_WORKER_EXECUTE_TIME,
                vertxOptions.getMaxWorkerExecuteTime()/1000000+vertxOptions.getMaxWorkerExecuteTime()/5000000+"");
        vertx = Vertx.vertx(vertxOptions);
        vertx.deployVerticle(WebClientVerticle.class.getName());
        vertx.deployVerticle(DatagramVerticle.class.getName());
        scriptEngineManager = new ScriptEngineManager();

        generateNames(Locale.getDefault());

        System.setProperty("java.security.policy",Res.createSecurityPolicyFile().toURI().toURL().toExternalForm());
        ApplicationSecurityManager applicationSecurityManager = new ApplicationSecurityManager();
        applicationSecurityManager.setGlobalAcessCode(GLOBAL_ACCESS_CODE);
        System.setSecurityManager(applicationSecurityManager);
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

    public static String getGlobalAccessCode() {
        if(Thread.currentThread() instanceof CustomerThread)
            return "fuck off";
        return GLOBAL_ACCESS_CODE;
    }
}
