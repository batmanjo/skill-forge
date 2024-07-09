package com.tech.skill.normal.config.sharding;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.hint.HintManager;
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
        log.info("collection:{},preciseShardingValue:{}", JSONObject.toJSONString(availableTargetNames), JSONObject.toJSONString(shardingValue));
        String hintTableName = shardingValue.getValues().stream().findFirst().orElse("");
        List<String> list = new ArrayList<>();
        availableTargetNames.forEach(tableName -> {
            String num = CharMatcher.inRange('0', '9').negate().removeFrom(tableName);
            // 这里可以多选
            if (StringUtils.equals(hintTableName, num)) {
                list.add(tableName);
            }

        });
        log.info("[MyHintTableShardingAlgorithm] tableName: {}", list);
        return list;
    }
}