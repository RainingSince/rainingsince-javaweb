package com.rainingsince.web.context;


import lombok.Data;

@Data
public class RequestContext<T> {
    private String path;
    private String userId;
    private T customParams;
}
