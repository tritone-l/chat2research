package com.research.chat.rsstart.controller;

import com.research.chat.rsstart.common.BaseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name：FileOperationController
 * @Author：tritone
 * @Date：2024/1/25 23:17
 */
@RestController("/file")
public class FileOperationController {

    @PostMapping(value = "/upload")
    public BaseResult upload(String fileName){
        return  BaseResult.success();
    }




}
