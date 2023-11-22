package com.tech.skill.forge.config.sharding;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yanmiao.wu
 * @create 2023-11-22 14:25
 */
@Slf4j
public class MyHintTableShardingAlgorithm implements HintShardingAlgorithm<String> {

    /**
     * <p>自定义使用强制路由器算法，路由到指定表</p>
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<String> shardingValue) {

        log.info("collection:" + JSONObject.toJSONString(availableTargetNames) + ",preciseShardingValue:" + JSONObject.toJSONString(shardingValue));
        Collection<String> usernames = shardingValue.getValues();
        List<String> list = new ArrayList<>();
        availableTargetNames.forEach(tableName -> {
            String num = CharMatcher.inRange('0', '9').negate().removeFrom(tableName);
            usernames.forEach(username -> {
                int hashNum = username.hashCode();
                int shardNum = Math.abs(hashNum) % 4;
                if(StringUtils.equals(String.valueOf(shardNum),num)){
                    list.add(tableName);
                }
            });
        });
        log.info("[MyHintTableShardingAlgorithm] tableName: {}",list);
        return list;
    }
}