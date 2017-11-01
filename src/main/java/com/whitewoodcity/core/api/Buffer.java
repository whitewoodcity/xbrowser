package com.whitewoodcity.core.api;

public class Buffer {
    private io.vertx.core.buffer.Buffer buffer;

    public Buffer(){
        buffer = io.vertx.core.buffer.Buffer.buffer();
    }

    public Buffer(io.vertx.core.buffer.Buffer buffer){
        this.buffer = buffer;
    }


}
