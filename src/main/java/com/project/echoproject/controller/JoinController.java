package com.project.echoproject.controller;

import com.project.echoproject.dto.ApiResponse;
import com.project.echoproject.dto.JoinDTO;
import com.project.echoproject.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {
    private final JoinService joinService;


    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }
    @PostMapping("/join")
    public ResponseEntity<ApiResponse> joinProcess(JoinDTO joinDTO){
        try {
            joinService.joinProcess(joinDTO);
            return new ResponseEntity<>(new ApiResponse("회원가입이 완료되었습니다."), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
