package com.project.echoproject.controller;

import org.aspectj.bridge.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/watch")
public class LoginWatch {
    ConcurrentHashMap<String, String> hs = new ConcurrentHashMap<>();
    @GetMapping("/phone/{pin}")
    public ResponseEntity<String> pinMade(@PathVariable String pin, @RequestHeader("Authorization") String token){
        hs.put(pin,token);
        return new ResponseEntity<>("발급 완료",HttpStatus.OK);
    }

    @GetMapping("/{pin}")
    public ResponseEntity<String> getInfo(@PathVariable String pin){
        HttpHeaders headers = new HttpHeaders();
        if(hs.get(pin) !=null){
            headers.add("Authorization", hs.get(pin));
            hs.remove(pin);

            return new ResponseEntity<>("발급완료",headers,HttpStatus.OK);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
