CREATE TABLE local_file_record(
    id            BIGINT(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_time` datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime      DEFAULT NULL COMMENT '更新时间',
    filename      VARCHAR(1024) DEFAULT NULL COMMENT '文件名',
    authors       VARCHAR(1024) DEFAULT NULL COMMENT '作者列表',
    digest        TEXT          DEFAULT NULL COMMENT '摘要',
    magazine      VARCHAR(1024) DEFAULT NULL COMMENT '杂志 ',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT = '本地文件记录表';