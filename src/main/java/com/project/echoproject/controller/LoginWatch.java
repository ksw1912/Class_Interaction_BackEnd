package com.project.echoproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/watch")
public class LoginWatch {
    private final ConcurrentHashMap<String, String> hs;
    private final ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public LoginWatch(ConcurrentHashMap<String, String> hs, ScheduledExecutorService scheduledExecutorService) {
        this.hs = hs;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @GetMapping("/phone/{pin}")
    public ResponseEntity<String> pinMade(@PathVariable String pin, @RequestHeader("Authorization") String token) {

        hs.put(pin, token);
        scheduledExecutorService.schedule(() -> {
            hs.remove(pin);
            scheduledExecutorService.shutdown();
        }, 60, TimeUnit.SECONDS);
        return new ResponseEntity<>("발급 완료", HttpStatus.OK);
    }

    @GetMapping("/{pin}")
    public ResponseEntity<String> getInfo(@PathVariable String pin) {
        HttpHeaders headers = new HttpHeaders();
        if (hs.get(pin) != null) {
            headers.add("Authorization", hs.get(pin));
            hs.remove(pin);
            return new ResponseEntity<>("발급완료", headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
