package com.tech.skill.normal.service.db;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tech.skill.normal.entity.User;
import com.tech.skill.normal.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yanmiao.wu
 * @create 2023-11-17 14:46
 */
@Service
public class UserSecureService extends ServiceImpl<UserMapper, User> {

    public User getUserByAccountId(String accountId){
        return this.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getAccountId,accountId));
    }

    public List<User> getUsersByTime(Date startTime,Date endTime){
        return this.baseMapper.selectList(Wrappers.<User>lambdaQuery()
                .between(User::getCreateTime,startTime,endTime));
    }

}
