package com.tech.skill.forge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tech.skill.forge.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanmiao.wu
 * @create 2023-11-17 14:16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
