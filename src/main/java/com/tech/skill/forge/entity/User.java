package com.tech.skill.forge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author yanmiao.wu
 * @create 2023-11-17 14:16
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    Integer id;
    String accountId;
    String password;
    String nickname;
    Date createTime;
    Date updateTime;
}
