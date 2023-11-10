package com.example.demo2.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app2")
public class Demo2ApplicationController {
    @GetMapping("/get")
    String getApp2(){
        return "App 2";
    }

   @GetMapping("/logs")
    public ResponseEntity<String> getLogs() throws IOException {
        Path logFile = Paths.get("mylog.log");
        String logs = new String(Files.readAllBytes(logFile));
        return ResponseEntity.ok(logs);
    }
    
}
