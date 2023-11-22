package com.tech.skill.forge.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.tech.skill.forge.config.sharding.MyHintTableShardingAlgorithm;
import com.tech.skill.forge.config.sharding.MyPreciseTableShardingAlgorithm;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.transaction.spring.ShardingTransactionTypeScanner;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author yanmiao.wu
 * @create 2023-11-22 14:21
 */

@Configuration
@MapperScan(basePackages = "com.tech.skill.forge.mapper", sqlSessionFactoryRef = "forgeShardingSqlSessionFactory")
public class ShardConfig {


    @Bean("forgeDataSource")
    public DataSource dataSource(@Qualifier("demo") DataSource forge) throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>(1);
        dataSourceMap.put("demo", forge);
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(getUserTableRuleConfiguration());

        Properties properties = new Properties();
        properties.setProperty("sql.show", Boolean.TRUE.toString());
        properties.setProperty("max.connections.size.per.query", "10");
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, properties);
    }

    @Bean("forgeShardingSqlSessionFactory")
    public SqlSessionFactory shardingSqlSessionFactory(@Qualifier("forgeDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setVfs(SpringBootVFS.class);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
        factoryBean.setTypeAliasesPackage("com.tech.skill.forge.entity");
        //自带完善的拦截器
        factoryBean.setPlugins(new MybatisPlusInterceptor());
        factoryBean.setGlobalConfig(GlobalConfigUtils.defaults());
        return factoryBean.getObject();
    }

    @Primary
    @Bean("forgeShardingTransactionManager")
    public DataSourceTransactionManager shardingTransactionManager(@Qualifier("forgeDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("forgeShardingSqlSessionTemplate")
    public SqlSessionTemplate shardingSqlSessionTemplate(@Qualifier("forgeShardingSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    private TableRuleConfiguration getHintUserTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("user","demo.user_$->{0..3}");
        result.setTableShardingStrategyConfig(new HintShardingStrategyConfiguration(new MyHintTableShardingAlgorithm()));
        return result;
    }

    private TableRuleConfiguration getUserTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("user","demo.user_$->{0..3}");
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_id",new MyPreciseTableShardingAlgorithm()));
        return result;
    }

    @Bean
    public ShardingTransactionTypeScanner shardingTransactionTypeScanner() {
        return new ShardingTransactionTypeScanner();
    }



}
