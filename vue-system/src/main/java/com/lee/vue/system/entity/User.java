package com.lee.vue.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "sys_user")
@Data
public class User {

    @TableId(type= IdType.AUTO)
    private String id;

    @TableField(value = "named")
    private String userName;

    private String password;

    private Integer enabled;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;
}
