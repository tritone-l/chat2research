package com.research.chat.domain.core.service;


import com.research.chat.domain.core.service.model.Literature;

/**
 * name：PdfHandlerService
 * Author：tritone
 * Date：2024/1/29  11:59
 */
public interface PdfHandlerService {
    void importFile(String absolutePath);

    /**
     * 获取本地所有文档
     */
    void getList();


    /**
     * 获取文档信息
     * @param fileName
     * @return
     */
    Literature getByName(String fileName);



}
