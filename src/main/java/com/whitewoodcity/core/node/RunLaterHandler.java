package com.whitewoodcity.core.node;

@FunctionalInterface
public interface RunLaterHandler<T,R> {
    R handle(T t);
}
