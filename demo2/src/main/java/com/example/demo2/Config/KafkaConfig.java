package com.example.demo2.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

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
                .startSpan();
        try {
            logger.info("Log :"+msg); 

        } finally {
            span.end();
        }
    }
    
}
