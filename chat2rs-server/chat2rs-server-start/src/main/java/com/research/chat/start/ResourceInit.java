package com.research.chat.start;

import com.research.chat.domain.repo.ConfigUtils;
import com.research.chat.domain.repo.Dbutils;

/**
 * @name：ResourceInit
 * @Author：tritone
 * @Date：2024/1/25 21:56
 */
public class ResourceInit {

    public static void init(){
        ConfigUtils.initProcess();
        initDb();
    }
    private static void initDb(){
        new Thread(() -> {
            Dbutils.init();
        }).start();
    }
}
