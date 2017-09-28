package com.whitewoodcity.core.node;

@FunctionalInterface
public interface ActionHandler<T> {
    void handle(T t);
}
