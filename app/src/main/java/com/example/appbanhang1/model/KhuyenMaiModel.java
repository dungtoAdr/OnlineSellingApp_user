package com.example.appbanhang1.model;

import java.util.List;

public class KhuyenMaiModel {
    boolean success;
    String message;
    List<KhuyenMai> result;

    public List<KhuyenMai> getResult() {
        return result;
    }

    public void setResult(List<KhuyenMai> result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
