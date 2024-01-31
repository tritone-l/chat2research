package com.research.chat.start.controller;

import com.research.chat.domain.core.service.PdfHandlerService;
import com.research.chat.start.common.BaseResult;
import com.research.chat.start.common.LiteratureVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @name：FileOperationController
 * @Author：tritone
 * @Date：2024/1/25 23:17
 */
@RestController()
@RequestMapping("/file")
public class FileOperationController {

    @Resource
    PdfHandlerService pdfHandlerService;

    /**
     * 导入文件 ，传入文件的绝对路径
     *
     * @param fileName
     * @return
     */
    @GetMapping(value = "/import")
    public BaseResult importFile(String fileName) {
        pdfHandlerService.importFile(fileName);
        return BaseResult.success();
    }

    @GetMapping(value = "/get")
    public BaseResult<LiteratureVO> getList(String fileName) {

        File file = new File(fileName);
        if (!file.exists()) {
            return BaseResult.error("文件不存在");
        }
        var literature = pdfHandlerService.getByName(file.getName());

        if (literature == null) {
            return BaseResult.success(new LiteratureVO());
        }
        var literatureVO = new LiteratureVO();
        BeanUtils.copyProperties(literature, literatureVO);

        return BaseResult.success(literatureVO);
    }
}
