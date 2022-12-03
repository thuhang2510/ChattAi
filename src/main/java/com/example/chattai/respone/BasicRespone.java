package com.example.chattai.respone;

import lombok.Data;

@Data
public class BasicRespone<T> {
    private String message;
    private int code;
    private T data;

    public BasicRespone(String message, int code, T data){
        this.data = data;
        this.code = code;
        this.message = message;
    }
}