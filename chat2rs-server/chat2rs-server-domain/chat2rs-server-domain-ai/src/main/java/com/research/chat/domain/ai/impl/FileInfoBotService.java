package com.research.chat.domain.ai.impl;

import com.research.chat.domain.ai.spark.endPoint.chat.req.ChatRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * name：FileInfoBotService
 * Author：tritone
 * Date：2024/1/30  22:09
 */
@Service
public class FileInfoBotService {

    public String getAuthorFromText(String text) {
        String pre = "把文本中的作者人名提取出来，用逗号分隔:";
        ChatRequest request = ChatRequest.baseBuild(pre + text);
        return AggregationSessionUtil.getResult(request);
    }

}
