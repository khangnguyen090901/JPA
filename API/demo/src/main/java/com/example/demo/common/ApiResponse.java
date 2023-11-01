package com.example.demo.common;

import java.time.LocalDateTime;

public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean IsSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return LocalDateTime.now().toString();
    }
}
