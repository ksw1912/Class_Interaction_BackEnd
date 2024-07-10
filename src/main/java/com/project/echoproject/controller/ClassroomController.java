package com.project.echoproject.controller;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.dto.ClassroomDTO;
import com.project.echoproject.dto.websocketDTO.MessageDTO;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Optional;

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

        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);
        System.out.println("토큰 email: "+email);

        return classroomService.createClassroom(classroomDTO,email);
    }

//    @GetMapping("/{id}")
//    public Optional<Classroom> getClassroomById(@PathVariable Long id) {
//        return classroomService.getClassroomById(id);
//    }

    @GetMapping
    public List<Classroom> getAllClassrooms() {
        return classroomService.getAllClassrooms();
    }

    @GetMapping("/{className}")
    public Optional<Classroom> getClassroomByClassName(@PathVariable String className) {
        return classroomService.getClassroomByClassName(className);
    }

//    @PutMapping("/{id}")
//    public Classroom updateClassroom(@PathVariable Long id, @RequestBody Classroom updatedClassroom) {
//        return classroomService.updateClassroom(id, updatedClassroom);
//    }

    @DeleteMapping("/{id}")
    public void deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
    }
}
