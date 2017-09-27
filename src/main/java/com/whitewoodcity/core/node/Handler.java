package com.whitewoodcity.core.node;

@FunctionalInterface
public interface Handler<T> {
    void handle(T t);
}
