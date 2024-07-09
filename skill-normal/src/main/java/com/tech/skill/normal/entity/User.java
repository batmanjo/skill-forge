package com.tech.skill.normal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author yanmiao.wu
 * @create 2023-11-17 14:16
 */
@Data
@TableName("user")
public class User {
    @Id
    @TableId(type = IdType.AUTO)
    Integer id;
    String accountId;
    String password;
    String nickname;
    Date createTime;
    Date updateTime;
}
