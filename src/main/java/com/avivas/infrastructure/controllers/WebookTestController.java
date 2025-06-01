package com.avivas.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebookTestController {
    @PostMapping("/webhook1")
    public ResponseEntity<Void>  webhook1(@RequestBody String request) {
        System.out.println(request);
        return ResponseEntity.ok().build();
    } 
    @PostMapping("/webhook2")
    public ResponseEntity<Void> webhook2(@RequestBody String request) {
        System.out.println(request);
        return ResponseEntity.ok().build();
    } 
}
