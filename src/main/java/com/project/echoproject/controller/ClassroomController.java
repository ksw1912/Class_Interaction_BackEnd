package com.project.echoproject.controller;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Opinion;
import com.project.echoproject.dto.classroom.ClassroomDTO;
import com.project.echoproject.dto.classroom.ClassroomResultDTO;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.ClassroomService;
import com.project.echoproject.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //api로 보내는 어노테이션임
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;
    private final OpinionService opinionService;
    private final JWTUtil jwtUtil;
    @Autowired
    public ClassroomController(ClassroomService classroomService ,JWTUtil jwtUtil,OpinionService opinionService) {
        this.classroomService = classroomService;
        this.jwtUtil = jwtUtil;
        this.opinionService = opinionService;
    }


    @PostMapping
    public ClassroomResultDTO createClassroomAndQuiz(@RequestBody ClassroomDTO classroomDTO, @RequestHeader("Authorization") String token) {
        System.out.println("createClassroomAndQuiz 메소드 실행");
        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);

        Classroom classroom = classroomService.createClassroom(classroomDTO,email);
        List<Opinion> ops = opinionService.createOpinion(classroom,classroomDTO);
        System.out.println("response하기 직전");
        return new ClassroomResultDTO(classroom,ops);
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
