package com.research.chat.domain.ai.spark.achieve.standard.interfaceSession;


public interface AggregationSession {

    ChatSession getChatSession();

    DocumentSession getDocumentSession();

    EmbeddingSession getEmbeddingSession();

    ImageSession getImageSession();
}
