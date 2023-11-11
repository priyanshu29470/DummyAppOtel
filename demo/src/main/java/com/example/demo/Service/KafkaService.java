package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private Tracer tracer;

    public boolean sendMSG(String msg){
        Span span = tracer.spanBuilder("kafka-producer")
                .setSpanKind(SpanKind.PRODUCER)
                .startSpan();
        try {
            for(int i=0; i<25; i++){
                this.kafkaTemplate.send("topic1",msg + i);
            }
            return true;
        } finally {
            span.end();
        }

    }

}
