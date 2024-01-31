package com.research.chat.start.controller;

import cn.hutool.core.util.StrUtil;
import com.research.chat.domain.ai.impl.AggregationSessionUtil;
import com.research.chat.domain.ai.spark.endPoint.chat.req.ChatRequest;
import com.research.chat.start.common.BaseResult;
import com.research.chat.start.common.LiteratureQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/**
 * name：AiChatController
 * Author：tritone
 * Date：2024/1/29  23:21
 */
@RestController()
@RequestMapping("/ai")
public class AiChatController {
    @GetMapping("/chat")
    public BaseResult<String> chat(LiteratureQuery queryRequest){
        Assert.notNull(queryRequest.getMessage(),"消息不能为空");
        ChatRequest request = ChatRequest.baseBuild( queryRequest.getMessage());
        String result =  AggregationSessionUtil.getResult(request);
        return BaseResult.success(result);
    }

}
