package com.rainingsince.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDataEntity implements Serializable, PageBuilder{
    @TableId("id")
    private String id;
    private String remark;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createDate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String createBy;
    private Long sort;
    @TableField(exist = false)
    private int current = 1;
    @TableField(exist = false)
    private int step = 10;
    @TableLogic
    private Integer deleted;

    public <T> IPage<T> toPage() {
        return new Page<>(current, step);
    }

    public <T> IPage<T> toPage(int current, int step) {
        return new Page<>(current, step);
    }
}
