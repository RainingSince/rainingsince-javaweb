package com.rainingsince.admin.plugs;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rainingsince.web.context.ApplicationProvider;
import com.rainingsince.web.context.RequestContext;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        RequestContext requestContext = ApplicationProvider.
                getRequestContext(RequestContext.class);
        if (StringUtils.isNotEmpty(requestContext.getUserId())) {
            if (StringUtils.isEmpty((String) this.getFieldValByName("createBy", metaObject)))
                this.setInsertFieldValByName("createBy",
                        requestContext.getUserId(), metaObject);
            this.setInsertFieldValByName("updateBy",
                    requestContext.getUserId(), metaObject);
        }
        this.setInsertFieldValByName("createDate",
                date, metaObject);
        this.setInsertFieldValByName("updateDate",
                date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date date = new Date();
        RequestContext requestContext = ApplicationProvider.
                getRequestContext(RequestContext.class);
        this.setUpdateFieldValByName("updateBy",
                requestContext.getUserId(), metaObject);
        this.setUpdateFieldValByName("updateDate",
                date, metaObject);
    }
}