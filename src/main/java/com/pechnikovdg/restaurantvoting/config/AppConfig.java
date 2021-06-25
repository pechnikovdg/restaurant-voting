package com.pechnikovdg.restaurantvoting.config;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.SQLException;

@Configuration
@Slf4j
@EnableCaching
@EnableScheduling
public class AppConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9093");
    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = "dishes", allEntries=true)
    public void clearCache() {
    }
}
