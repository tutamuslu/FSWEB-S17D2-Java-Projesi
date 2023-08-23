package com.workintech.dependencyproject.model;

public class DeveloperResponse {
    private int status;
    private String message;
    private Developer developer;

    public DeveloperResponse(int status, String message, Developer developer) {
        this.status = status;
        this.message = message;
        this.developer = developer;
    }
}
