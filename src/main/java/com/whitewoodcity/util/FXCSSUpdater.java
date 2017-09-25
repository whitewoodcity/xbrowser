package com.whitewoodcity.util;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.nio.charset.StandardCharsets;

/**
 * Class that handles the update of the CSS on the scene or any parent.
 *
 * Since in JavaFX, stylesheets can only be loaded from files or URLs, it implements a handler to create a magic "internal:stylesheet.css" url for our css string
 * see : https://github.com/fxexperience/code/blob/master/FXExperienceTools/src/com/fxexperience/tools/caspianstyler/CaspianStylerMainFrame.java
 * and : http://stackoverflow.com/questions/24704515/in-javafx-8-can-i-provide-a-stylesheet-from-a-string
 */
public class FXCSSUpdater {

    // URL Handler to create magic "internal:stylesheet.css" url for our css string
    {
        URL.setURLStreamHandlerFactory(new StringURLStreamHandlerFactory());
    }

    private String css;

    private Scene scene;

    public FXCSSUpdater(Scene scene) {
        this.scene = scene;
    }

    public void bindCss(StringProperty cssProperty){
        cssProperty.addListener(e -> {
            this.css = cssProperty.get();
            Platform.runLater(()->{
                scene.getStylesheets().clear();
                scene.getStylesheets().add("internal:stylesheet.css");
            });
        });
    }

    public void applyCssToParent(Parent parent){
        parent.getStylesheets().clear();
        scene.getStylesheets().add("internal:stylesheet.css");
    }

    /**
     * URLConnection implementation that returns the css string property, as a stream, in the getInputStream method.
     */
    private class StringURLConnection extends URLConnection {
        public StringURLConnection(URL url){
            super(url);
        }

        @Override
        public void connect() throws IOException {}

        @Override public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(css.getBytes(StandardCharsets.UTF_8.name()));
//            return IOUtils.toInputStream(css,"UTF-8");
        }
    }

    /**
     * URL Handler to create magic "internal:stylesheet.css" url for our css string
     */
    private class StringURLStreamHandlerFactory implements URLStreamHandlerFactory {

        URLStreamHandler streamHandler = new URLStreamHandler(){
            @Override
            protected URLConnection openConnection(URL url) throws IOException {
                if (url.toString().toLowerCase().endsWith(".css")) {
                    return new StringURLConnection(url);
                }
                throw new FileNotFoundException();
            }
        };

        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            if ("internal".equals(protocol)) {
                return streamHandler;
            }
            return null;
        }
    }
}