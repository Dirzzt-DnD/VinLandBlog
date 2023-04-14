package com.warzero.vinlandblog.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.warzero.vinlandblog.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AutoMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = SecurityUtils.getUserId();

        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}
