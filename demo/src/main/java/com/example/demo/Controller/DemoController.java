package com.example.demo.Controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.example.demo.Service.KafkaService;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;

@RestController
@RequestMapping("/app1")
public class DemoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    private final Tracer tracer;

    @Autowired
    private KafkaService kafkaService;

    private RestTemplate restTemplate;

    public DemoController(RestTemplate restTemplate, Tracer tracer) {
        this.restTemplate = restTemplate;
        this.tracer = tracer;
    }


    
    @GetMapping("/get")
    String getApp1(){
        Context currentContext = Context.current();
        Span currentSpan = Span.fromContext(currentContext);
        String traceId = currentSpan.getSpanContext().getTraceId();
        String spanId = currentSpan.getSpanContext().getSpanId();
        LOGGER.info("Trace Id fetched:{}",traceId);
        LOGGER.info("Span Id fetched:{}",spanId);
        return "App 1";
    }

    @GetMapping("/getDataFromApp2")
    String getDataFromApp2(){
        String uri = "http://localhost:8081/app2/get";;
        String res = restTemplate.getForObject(uri, String.class);
        return res;
    }

    @PostMapping("/kafka/send")
    public ResponseEntity<?> sendMSG(){
        this.kafkaService.sendMSG("Hello from Kakfa");
        return new ResponseEntity<>(Map.of("msg","sent"),HttpStatus.OK);
    }

    @GetMapping("/exception")
    public String exception() {
        throw new IllegalArgumentException("Some error");
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<String> handleConflict(IllegalArgumentException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
