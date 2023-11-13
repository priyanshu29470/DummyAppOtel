package com.example.demo2.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

@Configuration
public class KafkaConfig {

    Logger logger = LoggerFactory.getLogger(KafkaConfig.class); 

    @Autowired
    private Tracer tracer;

    @KafkaListener(topics = "topic1", groupId = "group-1")
    public void getMSG(String msg){
         Span span = tracer.spanBuilder("kafka-consumer")
                .setSpanKind(SpanKind.CONSUMER)
                .setParent(io.opentelemetry.context.Context.current())
                .startSpan();
        try {
            logger.info("Log :"+msg); 

        } finally {
            span.end();
        }
    }
     @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {


        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

         //The following code enable observation in the consumer listener  
        factory.getContainerProperties().setObservationEnabled(true);
        factory.setConsumerFactory(consumerFactory);


        return factory;
    }
    
}
