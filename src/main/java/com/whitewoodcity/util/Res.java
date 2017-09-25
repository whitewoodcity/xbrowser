package com.whitewoodcity.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class Res {

    public static URL getFxmlRes(String fxml){
        return ClassLoader.getSystemResource("fxml"+ File.separator+fxml+".fxml");
    }

    public static File getTempFile(String suffix) throws IOException{
        String path = System.getProperty("user.home");
        File tempDir=new File(path+"/whitewoodcity/xbrowser");
        //System.out.println(tempDir);
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        File file=new File(tempDir, UUID.randomUUID()+"."+suffix);
        if(!file.exists())
            file.createNewFile();
        return file;
    }
}
