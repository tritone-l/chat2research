package com.research.chat.rsstart;

import com.research.chat.chat2rsdomain.repo.Dbutils;
import com.research.chat.chat2rsdomain.util.ConfigUtils;

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
