package com.avivas.infrastructure.controllers;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebookTestController {
    private final AtomicInteger counter = new AtomicInteger(0);
    @PostMapping("/webhook")
    public ResponseEntity<Void>  webhook1(@RequestBody String request) {
        int val = this.counter.incrementAndGet();
        return val%4 == 0 ? ResponseEntity.ok().build() :  ResponseEntity.badRequest().build() ;
    }
}
