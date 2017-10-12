package com.whitewoodcity.thread;

public class CustomerThread extends Thread{
    public CustomerThread(Runnable runnable) {
        super(runnable);
    }
}