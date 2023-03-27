package com.example.blogging.config;

import com.example.blogging.payloads.CategoryDto;
import com.example.blogging.payloads.PostDto;
import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HazelcastConfig {

    @Bean
    public Config hazelCastConfig() {

        return new Config().setManagementCenterConfig(new ManagementCenterConfig()
                .setConsoleEnabled(true).setDataAccessEnabled(true).addTrustedInterface("127.0.0.1"));

    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelCastConfig) {
        return Hazelcast.newHazelcastInstance(hazelCastConfig);
    }

    @Bean
    public Map<String, CategoryDto> categoryMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("CategoryMap");
    }

    @Bean
    public Map<String, Long> likesMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("likeMap");
    }

    @Bean
    public Map<String, Long> dislikesMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("dislikeMap");
    }

//    @Bean
//    public Map<String, UserAccount> accountViewMap(HazelcastInstance hazelcastInstance) {
//        return hazelcastInstance.getMap("accountViewMap");
//    }

}
