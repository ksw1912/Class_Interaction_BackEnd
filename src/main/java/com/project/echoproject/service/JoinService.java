package com.project.echoproject.service;

import com.project.echoproject.domain.Instructor;
import com.project.echoproject.domain.Student;
import com.project.echoproject.dto.UserDTO;
import com.project.echoproject.repository.InstructorRepository;
import com.project.echoproject.repository.StudentRepository;
import com.project.echoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JoinService(UserRepository userRepository, StudentRepository studentRepository, InstructorRepository instructorRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        String role = userDTO.getRole();
        String department = userDTO.getDepartment();
        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new IllegalStateException("이메일이 존재합니다: " + email);
        }
        if ("student".equals(role)) {
            Student student = new Student();
            student.setUsername(username);
            student.setEmail(email);
            student.setPassword(bCryptPasswordEncoder.encode(password));
            student.setRole(role);
            student.setDepartment(department);
            studentRepository.save(student);
        } else if ("instructor".equals(role)) {
            Instructor instructor = new Instructor();
            instructor.setUsername(username);
            instructor.setEmail(email);
            instructor.setPassword(bCryptPasswordEncoder.encode(password));
            instructor.setRole(role);
            instructor.setDepartment(department);
            instructorRepository.save(instructor);
        } else {
            throw new IllegalStateException("유효하지 않은 역할입니다: " + role);
        }
    }

    public void checkEmail(UserDTO userDTO) {
        String email = userDTO.getEmail();
        Boolean isExist = userRepository.existsByEmail(email);

        if (!isExist) {
            System.out.println("이메일: "+ email);
            throw new IllegalStateException("사용가능한 이메일입니다.: " + email);
        }

         else {
            System.out.println("이메일중복스: "+ email);
           // throw new IllegalStateException("사용가능한 이메일입니다: " + email);
        }
    }

}
