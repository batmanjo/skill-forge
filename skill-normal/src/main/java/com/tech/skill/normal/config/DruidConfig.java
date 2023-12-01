package com.tech.skill.normal.config;

import com.alibaba.druid.pool.DruidDataSource;

import com.alibaba.druid.support.jakarta.WebStatFilter;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import jakarta.servlet.Filter;
import jakarta.servlet.Servlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author yanmiao.wu
 * @create 2023-11-22 14:47
 */

@Configuration
public class DruidConfig {
    @Bean("demo")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean<Servlet> druidServlet() {
        // 进行 druid 监控的配置处理
        ServletRegistrationBean<Servlet> srb = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 白名单
        srb.addInitParameter("allow", "127.0.0.1");
        // 黑名单
        srb.addInitParameter("deny", "");
        // 用户名
        srb.addInitParameter("loginUsername", "root");
        // 密码
        srb.addInitParameter("loginPassword", "123456");
        // 是否可以重置数据源
        srb.addInitParameter("resetEnable", "true");
        return srb;
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> frb = new FilterRegistrationBean<>();
        frb.setFilter((Filter) new WebStatFilter());
        // 所有请求进行监控处理
        frb.addUrlPatterns("/*");
        // 排除名单
        frb.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");
        return frb;
    }
}
