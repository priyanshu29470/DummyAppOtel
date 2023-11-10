package com.example.demo2.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConfig {

    Logger logger 
        = LoggerFactory.getLogger(KafkaConfig.class); 



    @KafkaListener(topics = "topic1", groupId = "group-1")
    public void getMSG(String msg){
        logger.info("Log :"+msg); 
    }
    
}
