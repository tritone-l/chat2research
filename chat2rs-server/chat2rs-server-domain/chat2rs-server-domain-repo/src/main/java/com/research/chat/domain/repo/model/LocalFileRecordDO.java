package com.research.chat.domain.repo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * name：LocalFileRecord
 * Author：tritone
 * Date：2024/1/30  23:27
 */
@Data
@TableName("local_file_record")
@NoArgsConstructor
public class LocalFileRecordDO {

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    private Date createTime;

    private Date updateTime;

    private String filename;

    private String authors;

    private String digest;

    private String magazine;

    public LocalFileRecordDO(String filename){
        this.filename = filename;
    }
}
