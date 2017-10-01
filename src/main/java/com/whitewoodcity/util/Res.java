package com.whitewoodcity.util;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
        File dir=new File(path+File.separator+"Whitewoodcity"+File.separator+"xbrowser");
        //System.out.println(tempDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    public static File getPluginDirectory() throws IOException{
        File dir = getDefaultDirectory();
        File pluginDir = new File(dir.getAbsolutePath()+File.separator+"plugin");
        if(!pluginDir.exists())
            pluginDir.mkdir();
        return pluginDir;
    }

    public static boolean isPluginExisted(String type, String version, String filename) throws IOException{
        File dir = getPluginDirectory(type, version);
        return new File(dir.getAbsolutePath()+File.separator+filename).exists();
    }

    public static File getPluginDirectory(String type, String version) throws IOException{
        File dir = getPluginDirectory();
        File pluginDir = new File(dir.getAbsolutePath()+File.separator+type);
        if(!pluginDir.exists())
            pluginDir.mkdir();
        dir = pluginDir;
        pluginDir = new File(dir.getAbsolutePath()+File.separator+version);
        if(!pluginDir.exists())
            pluginDir.mkdir();
        return pluginDir;
    }

    public static File getTempDirectory(String dirName) throws IOException{
        File dir = getDefaultDirectory();
        File temp = new File(dir.getAbsolutePath()+File.separator+dirName);

        if(!temp.exists()){
            temp.mkdir();
        }
        return temp;
    }

    public static void removeTempDirectory(File dir) throws IOException{
        if(dir == null) return;
        if(dir.list()!=null) {
            for (String s : dir.list()) {
                File currentFile = new File(dir.getPath(), s);
                currentFile.delete();
            }
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

    public static void downLoadFromUrl(String urlStr, File dir, String fileName,StringProperty progressProperty){

        File file = new File(dir+File.separator+fileName);
        int i = Integer.parseInt(progressProperty.get());
        try {
            URLConnection conn = new URL(urlStr).openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            try(InputStream inputStream = conn.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file)){
                int c;
                while ((c = inputStream.read()) != -1) {
                    fileOutputStream.write(c);
                    i++;
                    if(i%1024==0){
                        int k = i;
                        Platform.runLater(()-> {
                            progressProperty.set(k+"");
                        });
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
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
