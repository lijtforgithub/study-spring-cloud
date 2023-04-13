package com.ljt.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-04-13 11:27
 */
@Data
public class Log {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;

}
