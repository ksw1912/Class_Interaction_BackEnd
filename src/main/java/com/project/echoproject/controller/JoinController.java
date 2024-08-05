package com.project.echoproject.controller;

import com.project.echoproject.dto.ApiResponse;
import com.project.echoproject.dto.UserDTO;
import com.project.echoproject.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {
    private final JoinService joinService;
    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> joinProcess(@RequestBody UserDTO userDTO) {
        try {
            joinService.joinProcess(userDTO);
            return new ResponseEntity<>(new ApiResponse("회원가입이 완료되었습니다."), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<ApiResponse> checkEmail(@RequestBody UserDTO userDTO) {
        try {
            joinService.checkEmail(userDTO);
            return new ResponseEntity<>(new ApiResponse("이메일사용가능"), HttpStatus.OK);

        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
