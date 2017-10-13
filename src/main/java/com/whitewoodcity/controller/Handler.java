package com.whitewoodcity.controller;

@FunctionalInterface
public interface Handler {
    Object handle(String accessCode) throws Exception;
}

