package com.ljt.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-04-13 10:57
 */
@Data
@TableName("t")
public class Test {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String code;
    private String name;

}
