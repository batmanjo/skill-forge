package com.tech.skill.forge.service.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tech.skill.forge.entity.User;
import com.tech.skill.forge.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author yanmiao.wu
 * @create 2023-11-17 14:46
 */
@Service
public class UserSecureService extends ServiceImpl<UserMapper, User> {

}
