package com.example.demo.Config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic(){

        return TopicBuilder.name("topic1")
        .replicas(1)
        .build();
    }


@Bean
public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
    KafkaTemplate<String, String> stringStringKafkaTemplate = new KafkaTemplate<>(producerFactory);
    stringStringKafkaTemplate.setObservationEnabled(true);//trying out with
    return stringStringKafkaTemplate;
}
}
