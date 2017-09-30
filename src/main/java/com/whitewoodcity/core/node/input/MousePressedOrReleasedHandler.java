package com.whitewoodcity.core.node.input;

@FunctionalInterface
public interface MousePressedOrReleasedHandler {
    void handle(double x, double y);
}
