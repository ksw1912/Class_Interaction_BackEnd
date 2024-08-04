package com.project.echoproject.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Enrollment;
import com.project.echoproject.domain.User;
import com.project.echoproject.dto.CustomUserDetails;
import com.project.echoproject.dto.InstructorResponseDTO;
import com.project.echoproject.dto.LoginDTO;
import com.project.echoproject.dto.StudentResponseDTO;
import com.project.echoproject.repository.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserRepository userRepository, ClassroomRepository classroomRepository, StudentRepository studentRepository, InstructorRepository instructorRepository, EnrollmentRepository enrollmentRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;

        // 필터가 /login 경로에 대해서만 작동하도록 설정가능
        //setFilterProcessesUrl("/login");
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 username, password 추출
        try {
            LoginDTO loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            request.setCharacterEncoding("UTF-8");
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            //스프링 시큐리티에서 email과 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

            //token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String email = customUserDetails.getUsername();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(email, role, 7200000L); //2시간
        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        User user;
        if (role.equals("student")) {
            user = studentRepository.findByEmail(email);
            user.setPassword(null);
            List<Enrollment> enrollments = enrollmentRepository.findByStudentEmail(email);
            response.getWriter().write(new ObjectMapper().writeValueAsString(new StudentResponseDTO(user, enrollments)));
        } else if (role.equals("instructor")) {
            user = instructorRepository.findByEmail(email);
            List<Classroom> instructorInfo = classroomRepository.findByInstructorEmail(email);
            user.setPassword(null);
            InstructorResponseDTO resp = new InstructorResponseDTO();
            resp.setUser(user);
            resp.setClassrooms(instructorInfo);
            response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
        } else {
            user = userRepository.findByEmail(email);
            user.setPassword(null);
            response.getWriter().write(new ObjectMapper().writeValueAsString(user));
        }
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        //로그인 실패시 401 응답 코드 반환
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        response.getWriter().write("{\"message\": \"아이디 또는 비밀번호가 다릅니다.: " + "\"}");
    }
}
