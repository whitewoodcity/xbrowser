package com.whitewoodcity.core.node.input;

@FunctionalInterface
public interface MouseMovedHandler {
    void handle(double x, double y);
}
