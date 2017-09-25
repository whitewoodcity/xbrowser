package com.whitewoodcity.util;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

public class Res {

    public static URL getFxmlRes(String fxml){
        return ClassLoader.getSystemResource("fxml"+ File.separator+fxml+".fxml");
    }

    public static File getTempFile(String suffix) throws IOException{
        String path = System.getProperty("user.home");
        File tempDir=new File(path+"/Whitewoodcity/xbrowser");
        //System.out.println(tempDir);
        if(!tempDir.exists()){
            tempDir.mkdirs();
        }
        File file=new File(tempDir, UUID.randomUUID()+"."+suffix);
        if(!file.exists())
            file.createNewFile();
        return file;
    }

    public static void saveFile(File file, String content) throws IOException{
        file.delete();
        file.createNewFile();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"))) {
            writer.write(content);
        }
    }
}
