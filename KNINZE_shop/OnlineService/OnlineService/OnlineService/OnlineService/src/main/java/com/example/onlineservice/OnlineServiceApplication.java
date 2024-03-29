package com.example.onlineservice;

import com.example.core.AxonXstreamConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


@EnableDiscoveryClient
@SpringBootApplication
@Import({AxonXstreamConfig.class})
public class OnlineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineServiceApplication.class, args);
        System.out.println("Online Service is running");
    }

}
