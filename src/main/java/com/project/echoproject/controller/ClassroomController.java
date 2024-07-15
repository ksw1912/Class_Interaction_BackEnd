package com.project.echoproject.controller;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.dto.ApiResponse;
import com.project.echoproject.dto.ClassroomDTO;
import com.project.echoproject.dto.websocketDTO.MessageDTO;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController //api로 보내는 어노테이션임
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;
    private final JWTUtil jwtUtil;
    @Autowired
    public ClassroomController(ClassroomService classroomService ,JWTUtil jwtUtil) {
        this.classroomService = classroomService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Classroom createClassroom(@RequestBody ClassroomDTO classroomDTO, @RequestHeader("Authorization") String token) {
        System.out.println("createClassroom 메서드가 호출");
        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);
        System.out.println("토큰 email: "+email);
        return classroomService.createClassroom(classroomDTO,email);
    }
//    // 특정 교수의 이메일로 클래스룸 목록 가져오기
//    @GetMapping("/instructor/{email}")
//    public List<Classroom> getClassroomsByInstructorEmail(@PathVariable String email) {
//        List<Classroom> classrooms = classroomService.getClassroomsByInstructorEmail(email);
//        return classrooms;
//    }
//
//    @GetMapping("/classroomEnter/{id}")
//    public Optional<Classroom> getClassroomById(@PathVariable UUID id) {
//        return classroomService.getClassroomById(id);
//    }
//
//    @GetMapping
//    public List<Classroom> getAllClassrooms() {
//        return classroomService.getAllClassrooms();
//    }
//
//    //안쓸 수도
//    @GetMapping("/{className}")
//    public Optional<Classroom> getClassroomByClassName(@PathVariable String className) {
//        return classroomService.getClassroomByClassName(className);
//    }
//    @DeleteMapping("/classroomDelete/{id}")
//    public ApiResponse deleteClassroom(@PathVariable UUID id) {
//        classroomService.deleteClassroom(id);
//        return new ApiResponse("수업 삭제 성공");
//    }

//    @PutMapping("/{id}")
//    public Classroom updateClassroom(@PathVariable Long id, @RequestBody Classroom updatedClassroom) {
//        return classroomService.updateClassroom(id, updatedClassroom);
//    }

}
