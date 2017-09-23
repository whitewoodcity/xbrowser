package com.whitewoodcity.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Res {


    public static URL getFxmlRes(String fxml){
        return ClassLoader.getSystemResource("fxml"+ File.separator+fxml+".fxml");
    }

    public static File getTempFile(){
        File tempDir=new File(ClassLoader.getSystemResource("temp").getPath());
        System.out.println(tempDir);
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        File file=new File(tempDir,System.currentTimeMillis()+".css");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
