package com.research.chat.domain.core.service.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * name：
 * Author：tritone
 * Date：2024/1/31  16:46
 */
@Data
public class  Literature{
    private Long id;

    private Date createTime;

    private Date updateTime;

    private String filename;

    private String authors;

    private String digest;

    private String magazine;

}
