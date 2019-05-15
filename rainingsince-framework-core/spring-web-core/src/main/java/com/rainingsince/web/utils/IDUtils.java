package com.rainingsince.web.utils;

import java.util.UUID;

public class IDUtils {

    /**
     * 随机id生成，使用雪花算法
     *
     * @return 唯一ID
     */
    public static long getRandomId() {
        SnowflakeIdWorker sf = new SnowflakeIdWorker();
        return sf.nextId();
    }

    public static long getIDWithUUID() {
        return Long.parseLong(UUID.randomUUID().toString().replace("-", ""));
    }


}
