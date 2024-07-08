package com.project.echoproject.service;

import com.project.echoproject.domain.User;
import com.project.echoproject.dto.JoinDTO;
import com.project.echoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        String role = joinDTO.getRole();
        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new IllegalStateException("이메일이 존재합니다: " + email);
        }

        User data = new User();
        data.setEmail(email);
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole(role);

        userRepository.save(data);
    }
}
