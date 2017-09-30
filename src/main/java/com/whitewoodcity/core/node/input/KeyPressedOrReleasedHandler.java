package com.whitewoodcity.core.node.input;

@FunctionalInterface
public interface KeyPressedOrReleasedHandler {
    void handle(String code);
}
