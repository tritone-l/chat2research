package com.research.chat.rsstart;

import com.research.chat.chat2rsdomain.repo.Dbutils;

/**
 * @name：ResourceInit
 * @Author：tritone
 * @Date：2024/1/25 21:56
 */
public class ResourceInit {

    public static void init(){
        initDb();
    }
    private static void initDb(){
//        new Thread(() -> {
//            Dbutils.init();
//        }).start();
        Dbutils.init();
    }
}
