package com.whitewoodcity.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

public class Res {

    public static URL getFxmlRes(String fxml){
        return ClassLoader.getSystemResource("fxml"+ File.separator+fxml+".fxml");
    }

    public static File getDefaultDirectory() throws IOException{
        String path = System.getProperty("user.home");
        File dir=new File(path+"/Whitewoodcity/xbrowser");
        //System.out.println(tempDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    public static File getTempDirectory(String dirName) throws IOException{
        File dir = getDefaultDirectory();
        File temp = new File(dir.getAbsolutePath()+"/"+dirName);

        if(!temp.exists()){
            temp.mkdir();
        }
        return temp;
    }

    public static void removeTempDirectory(File dir) throws IOException{
        String[] entries = dir.list();
        if(entries==null) return;
        for(String s: entries){
            File currentFile = new File(dir.getPath(),s);
            currentFile.delete();
        }
        dir.delete();
    }

    public static File getTempFile(String suffix) throws IOException{
        File dir = getDefaultDirectory();
        File file=new File(dir, UUID.randomUUID()+"."+suffix);
        if(!file.exists())
            file.createNewFile();
        return file;
    }

    public static File getTempFile(File dir, String suffix) throws IOException{
        File file=new File(dir, UUID.randomUUID()+"."+suffix);
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

    public static URL getExternalJar(JarRes jar){
        try {
            File file=jar.get();
            if(file.exists()){
                return file.toURI().toURL();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
