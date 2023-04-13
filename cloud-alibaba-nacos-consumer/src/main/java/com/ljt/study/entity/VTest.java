package com.ljt.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-04-13 11:32
 */
@Data
@TableName("v_t")
public class VTest {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;


}
