package com.research.chat.domain.ai.spark.achieve.standard.interfaceSession;


import com.research.chat.domain.ai.spark.endPoint.embedding.req.EmbeddingRequest;
import com.research.chat.domain.ai.spark.endPoint.embedding.resp.EmbeddingResponse;

/**
 * 文本嵌入场景下的接口
 */
public interface EmbeddingSession {

    /**
     * 文本嵌入
     *
     * @param embeddingRequest 请求参数
     * @return 请求结果
     */
    EmbeddingResponse embed(EmbeddingRequest embeddingRequest);

    /**
     * 文本嵌入
     *
     * @param apiKey           用户的ApiKey
     * @param apiSecret        用户的ApiSecret
     * @param embeddingRequest 请求参数
     * @return 请求结果
     */
    EmbeddingResponse embed(String apiKey, String apiSecret, EmbeddingRequest embeddingRequest);

}
