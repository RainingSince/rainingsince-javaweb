package com.rainingsince.web.version;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiVersionRequestCondition implements RequestCondition<ApiVersionRequestCondition> {
    private static final Pattern VERSION_PATTERN = Pattern.compile("/v(\\d+).*");
    private int version;
    private static int maxVersion = 1;

    public ApiVersionRequestCondition(int version) {
        this.version = version;
    }

    @Override
    public ApiVersionRequestCondition combine(ApiVersionRequestCondition apiVersionRequestCondition) {
        return new ApiVersionRequestCondition(apiVersionRequestCondition.version);
    }

    @Override
    public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        Matcher matcher = VERSION_PATTERN.matcher(httpServletRequest.getRequestURI());
        if (matcher.find()) {
            String versionNo = matcher.group(1);
            int version = Integer.valueOf(versionNo);
            if (version >= this.version) {

                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionRequestCondition apiVersionRequestCondition, HttpServletRequest httpServletRequest) {
        return apiVersionRequestCondition.version - this.version;
    }

    public int getVersion() {
        return version;
    }

    public static void setMaxVersion(int maxVersion) {
        ApiVersionRequestCondition.maxVersion = maxVersion;
    }
}
