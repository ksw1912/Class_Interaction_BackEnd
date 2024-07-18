package com.project.echoproject.controller;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Opinion;
import com.project.echoproject.dto.ApiResponse;
import com.project.echoproject.dto.classroom.ClassroomDTO;
import com.project.echoproject.dto.classroom.ClassroomResultDTO;
import com.project.echoproject.dto.classroom.UpdateOpinionDTO;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.ClassroomService;
import com.project.echoproject.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);

        Classroom classroom = classroomService.createClassroom(classroomDTO,email);
        List<Opinion> ops = opinionService.createOrUpdateOpinion(classroom,classroomDTO);
        return new ClassroomResultDTO(classroom,ops);
    }
    @PostMapping("/opinons/update")
    public List<Opinion>  updateOpinions(@RequestBody UpdateOpinionDTO updateOpinionDTO, @RequestHeader("Authorization") String token){
        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);
        Optional<Classroom> classroom = classroomService.getClassroomById(updateOpinionDTO.getClassId());
        return opinionService.UpdateOpinion(updateOpinionDTO,email,classroom);
    }

    @DeleteMapping("/classDelete/{id}")
    public ApiResponse deleteClassroom(@PathVariable UUID id) {
        classroomService.deleteClassroom(id);
        return new ApiResponse("수업 삭제 성공");
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

//    @PutMapping("/{id}")
//    public Classroom updateClassroom(@PathVariable Long id, @RequestBody Classroom updatedClassroom) {
//        return classroomService.updateClassroom(id, updatedClassroom);
//    }

}
