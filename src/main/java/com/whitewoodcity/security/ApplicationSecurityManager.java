package com.whitewoodcity.security;

import com.whitewoodcity.thread.CustomerThread;

import java.util.UUID;

public class ApplicationSecurityManager extends SecurityManager {

    private String globalAcessCode;

    public void setGlobalAcessCode(String globalAcessCode) {
        this.globalAcessCode = globalAcessCode;
    }

    @Override
    public void checkAccess(ThreadGroup g) {
        if(Thread.currentThread() instanceof CustomerThread){

            if(!Thread.currentThread().getName().equals(globalAcessCode)){
                throw new SecurityException("Security error.");
            }

            else Thread.currentThread().setName(UUID.randomUUID().toString());
        }
        super.checkAccess(g);
    }
}