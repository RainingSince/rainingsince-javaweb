package com.rainingsince.web.context;

import javax.servlet.http.HttpServletRequest;

public interface RequestContextExecutor {

    RequestContext execute(HttpServletRequest request,RequestContext context);

}
