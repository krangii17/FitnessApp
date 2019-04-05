package com.FitnessApp.utils.dtos;

import java.util.HashMap;
import java.util.Map;

public enum ControllerMessage {
    STATUS_200("Operation successful") {
        @Override
        public Map<String, String> build(String obName) {
            response.clear();
            response.put(obName, ControllerMessage.STATUS_200.message);
            return response;
        }
    },
    STATUS_201("is created") {
        @Override
        public Map<String, String> build(String obName) {
            response.clear();
            response.put(obName, ControllerMessage.STATUS_201.message);
            return response;
        }
    },
    STATUS_404("not found") {
        @Override
        public Map<String, String> build(String obName) {
            response.clear();
            response.put(obName, ControllerMessage.STATUS_404.message);
            return response;
        }
    },
    STATUS_412("Precondition Failed") {
        @Override
        public Map<String, String> build(String obName) {
            response.clear();
            response.put(obName, ControllerMessage.STATUS_412.message);
            return response;
        }
    },
    STATUS_400("Bad request. Check request parameters") {
        @Override
        public Map<String, String> build(String obName) {
            response.clear();
            response.put(obName, ControllerMessage.STATUS_400.message);
            return response;
        }
    },
    STATUS_409("already exist") {
        @Override
        public Map<String, String> build(String obName) {
            response.clear();
            response.put(obName, ControllerMessage.STATUS_409.message);
            return response;
        }
    };

    private static Map<String, String> response = new HashMap<>();
    private String message;

    ControllerMessage(String message) {
        this.message = message;
    }

    public abstract Map<String, String> build(String obName);
}
