package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public boolean sendMSG(String msg){
        for(int i=0; i<25; i++){
            this.kafkaTemplate.send("topic1",msg + i);
        }
        return true;
    }
}
