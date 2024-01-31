package com.research.chat.domain.ai.spark.common.strategy.impl;

import com.research.chat.domain.ai.spark.common.strategy.KeyStrategy;

import java.util.List;


public class FirstKeyStrategy<T> implements KeyStrategy<List<T>, T> {

    @Override
    public T apply(List<T> keyList) {
        return keyList.get(0);
    }
}
