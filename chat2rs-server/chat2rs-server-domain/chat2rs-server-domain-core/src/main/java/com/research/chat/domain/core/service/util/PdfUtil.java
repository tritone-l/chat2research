package com.research.chat.domain.core.service.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * name：PdfUtil
 * Author：tritone
 * Date：2024/1/30  23:50
 */
@Slf4j
public class PdfUtil {

    public static String getPdfText(String file,int pageIndex){
        try  {
            PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile(file));
            var pdPage = document.getPage(pageIndex);

            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            // 设置仅提取第一页
            pdfTextStripper.setStartPage(1);
            pdfTextStripper.setEndPage(1);
            // 从文档中提取文本
            String text = pdfTextStripper.getText(document);
            return text;
        } catch (IOException e) {
            log.error("文件读取错误",e);
        }
        return null;

    }


}
