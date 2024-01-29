package com.research.chat.start.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @name：MainController
 * @Author：tritone
 * @Date：2024/1/24 17:30
 */
@RestController
public class MainController {

    @GetMapping("/docs")
    public List<String> index(){
        return List.of("文档1，文档2，文档3");
    }

    @GetMapping("/exit")
    public String exit(){
        new Thread(()-> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        }).start();
        return "exit";


    }

}
