package com.whitewoodcity.util;

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

    public static void  downLoadFromUrl(String urlStr,File dir,String fileName) throws IOException{
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        File file = new File(dir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }

    }

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
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
