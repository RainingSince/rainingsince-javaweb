package com.rainingsince.web.autoconfigure;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rainingsince")
public class ProjectProperties {
    private String tokenKey = "ZmFzdGVyLWZyYW1ld29yaw==";
    private boolean versionEnable = false;


    public String getTokenKey() {
        return tokenKey;
    }

    public boolean isVersionEnable() {
        return versionEnable;
    }

    public void setVersionEnable(boolean versionEnable) {
        this.versionEnable = versionEnable;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }
}
