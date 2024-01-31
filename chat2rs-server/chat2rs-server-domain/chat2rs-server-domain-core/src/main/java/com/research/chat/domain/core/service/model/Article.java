package com.research.chat.domain.core.service.model;

import lombok.Data;

import java.util.List;

/**
 * name：Article
 * Author：tritone
 * Date：2024/1/29  12:01
 */
@Data
public class Article {

    String name;

    /**
     * 条目类型
     */
    String type;

    /**
     * 期刊/杂志
     */
    String magazine;

    /**
     * 摘要
     */
    String digest;
}
