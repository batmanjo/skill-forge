package com.tech.skill.normal.config.sharding;

import com.google.common.base.CharMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author yanmiao.wu
 * @create 2023-11-22 17:25
 */
@Slf4j
public class MyPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        log.info("[MyPreciseTableShardingAlgorithm] availableTargetNames : {}, PreciseShardingValue : {}", availableTargetNames, shardingValue);
        String accountId = shardingValue.getValue();
        if (accountId == null) {
            log.error("分表健username为null");
            throw new UnsupportedOperationException("username is null");
        }

        return availableTargetNames.stream().filter(tableName -> {
            String num = CharMatcher.inRange('0', '9').negate().removeFrom(tableName);
            int hashNum = accountId.hashCode();
            int shardNum = Math.abs(hashNum) % 4;
            return StringUtils.equals(num, String.valueOf(shardNum));
        }).findFirst().orElse(null);

    }
}
