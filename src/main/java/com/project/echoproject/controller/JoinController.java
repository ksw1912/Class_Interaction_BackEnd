package com.project.echoproject.controller;

import com.project.echoproject.dto.JoinDTO;
import com.project.echoproject.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }
    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);
        return "ok";
    }
}
