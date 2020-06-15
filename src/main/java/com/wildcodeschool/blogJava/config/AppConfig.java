package com.wildcodeschool.blogJava.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class AppConfig {
    private String hello;
    public MysqlConfig mysql;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public MysqlConfig getMysql() {
        return mysql;
    }

    public void setMysql(MysqlConfig mysql) {
        this.mysql = mysql;
    }
}