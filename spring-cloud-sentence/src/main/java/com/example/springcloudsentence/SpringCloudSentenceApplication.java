package com.example.springcloudsentence;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudSentenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudSentenceApplication.class, args);
    }

    /**
     * A source of threads.
     */
    @Bean
    public Executor executor() {
        return Executors.newFixedThreadPool(6);
    }

}
