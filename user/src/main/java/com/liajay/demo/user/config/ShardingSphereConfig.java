package com.liajay.demo.user.config;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

//@Configuration
//public class ShardingSphereConfig {
//    @Bean
//    public DataSource shardingDataSource() throws IOException, SQLException {
//        // 从 classpath 加载配置
//        Resource resource = new ClassPathResource("sharding-rules.yml");
//        try (InputStream inputStream = resource.getInputStream()) {
//            return YamlShardingSphereDataSourceFactory.createDataSource(inputStream);
//        }
//    }
//
//}
