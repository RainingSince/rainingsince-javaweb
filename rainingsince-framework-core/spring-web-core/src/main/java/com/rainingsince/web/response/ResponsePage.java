package com.rainingsince.web.response;

public class ResponsePage<T> {
    private int total;
    private int current;
    private int step;
    private T data;
}
