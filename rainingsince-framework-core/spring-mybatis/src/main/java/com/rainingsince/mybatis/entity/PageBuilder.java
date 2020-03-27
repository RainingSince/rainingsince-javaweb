package com.rainingsince.mybatis.entity;


import com.baomidou.mybatisplus.core.metadata.IPage;

public interface PageBuilder {
    <T> IPage<T> toPage();

    <T> IPage<T> toPage(int current, int step);
}
