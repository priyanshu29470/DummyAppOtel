package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Service.KafkaService;

@RestController
@RequestMapping("/app1")
public class DemoController {

    @Autowired
    private KafkaService kafkaService;
    
    @GetMapping("/get")
    String getApp1(){
        return "App 1";
    }

    @GetMapping("/getDataFromApp2")
    String getDataFromApp2(){
        String uri = "http://localhost:8081/app2/get";
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(uri, String.class);
        return res;
    }

    @PostMapping("/kafka/send")
    public ResponseEntity<?> sendMSG(){
        this.kafkaService.sendMSG("Hello from Kakfa");
        return new ResponseEntity<>(Map.of("msg","sent"),HttpStatus.OK);
    }
}
