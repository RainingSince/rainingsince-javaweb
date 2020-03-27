package com.rainingsince.mybatis.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class BaseRequestData implements PageBuilder {
    private int current;
    private int step;

    public <T> IPage<T> toPage() {
        return new Page<>(current, step);
    }

    public <T> IPage<T> toPage(int current, int step) {
        return new Page<>(current, step);
    }

}
