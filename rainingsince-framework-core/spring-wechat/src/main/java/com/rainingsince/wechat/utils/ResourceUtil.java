package com.rainingsince.wechat.utils;

import org.springframework.core.io.ClassPathResource;


public class ResourceUtil {

    public static ClassPathResource getResourceWithPath(String path) {
        return new ClassPathResource(path);
    }
}
