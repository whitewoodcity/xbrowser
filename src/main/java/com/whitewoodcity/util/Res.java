package com.whitewoodcity.util;

import java.io.File;
import java.net.URL;

public class Res {

    public static URL getFxmlRes(String fxml){
        return ClassLoader.getSystemResource("fxml"+ File.separator+fxml+".fxml");
    }
}
