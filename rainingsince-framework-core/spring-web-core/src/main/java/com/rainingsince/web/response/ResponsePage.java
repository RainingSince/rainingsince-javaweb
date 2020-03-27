package com.rainingsince.web.response;

import lombok.Data;

@Data
public class ResponsePage<T> {
    private int total;
    private int current;
    private int step;
    private T data;
}
