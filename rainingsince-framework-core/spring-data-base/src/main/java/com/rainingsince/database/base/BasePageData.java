package com.rainingsince.database.base;

import lombok.Data;
import java.util.List;

@Data
public class BasePageData<T> {
    private int current;
    private int step;
    private int total;
    private List<T> dataList;
}
