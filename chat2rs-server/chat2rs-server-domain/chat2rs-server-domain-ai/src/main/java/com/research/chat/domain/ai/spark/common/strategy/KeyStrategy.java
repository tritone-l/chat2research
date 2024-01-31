package com.research.chat.domain.ai.spark.common.strategy;


/**
 * @Description: API Key 获取策略
 */
public interface KeyStrategy<T, R> {
    R apply(T t);
}
