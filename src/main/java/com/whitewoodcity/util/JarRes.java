package com.whitewoodcity.util;

import java.io.File;
public enum JarRes {

    JRUBY{
        @Override
        public File get() {
            String path = System.getProperty("user.home");
            File tempDir=new File(path+"/Whitewoodcity/xbrowser/plugins");
            if(!tempDir.exists()){
                tempDir.mkdirs();
            }

            File groovy=new File(tempDir,"jruby-complete-9.1.13.0.jar");
            return groovy;
        }
    },
    GROOVY{
        @Override
        public File get() {
            String path = System.getProperty("user.home");
            File tempDir=new File(path+"/Whitewoodcity/xbrowser/plugins");
            if(!tempDir.exists()){
                tempDir.mkdirs();
            }

            File groovy=new File(tempDir,"groovy_plugin.jar");
            return groovy;
        }
    };


    public abstract File get();
}
