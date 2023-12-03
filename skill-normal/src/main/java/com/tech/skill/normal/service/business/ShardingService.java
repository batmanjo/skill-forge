package com.tech.skill.normal.service.business;

import com.tech.skill.normal.entity.User;
import com.tech.skill.normal.service.db.UserSecureService;
import jakarta.annotation.Resource;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yanmiao.wu
 * @create 2023-11-22 17:02
 */

@Service
public class ShardingService {

    @Resource
    UserSecureService userSecureService;

    /**
     * 启用MyHintTableShardingAlgorithm
     */
    public User saveUserByHint(String accountId){
        User user = new User();
        user.setCreateTime(new Date());
        user.setAccountId(accountId);
        //不设置hint的情况下,经测试，会生成4条SQL分别保存4张表
//        userSecureService.save(user);
        //设置Hint的情况下,只保存了user_0
        HintManager.getInstance().addTableShardingValue("user","99999999");
        userSecureService.save(user);
        return user;
    }

    /**
     * 启用MyPreciseTableShardingAlgorithm
     */
    public User saveUserByPrecise(String accountId){
        User user = new User();
        user.setCreateTime(new Date());
        user.setAccountId(accountId);
        //不设置hint的情况下,按自定义规则执行
//        userSecureService.save(user);
        //设置Hint的情况下,配置不会生效，继续走默认规则
        HintManager.clear();
        HintManager.getInstance().addTableShardingValue("user","99999999");
        userSecureService.save(user);
        return user;
    }

}
