package com.project.echoproject.controller;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Enrollment;
import com.project.echoproject.domain.Opinion;
import com.project.echoproject.dto.ApiResponse;
import com.project.echoproject.dto.classroom.ClassroomDTO;
import com.project.echoproject.dto.classroom.ClassroomResultDTO;
import com.project.echoproject.dto.classroom.ResultUpdateClassroomDTO;
import com.project.echoproject.dto.classroom.UpdateClassroomDTO;
import com.project.echoproject.jwt.JWTUtil;
import com.project.echoproject.service.ClassroomService;
import com.project.echoproject.service.EnrollmentService;
import com.project.echoproject.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

@RestController //api로 보내는 어노테이션임
@RequestMapping("/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;
    private final OpinionService opinionService;
    private final EnrollmentService enrollmentService;
    private final JWTUtil jwtUtil;

    public ClassroomController(ClassroomService classroomService, OpinionService opinionService, EnrollmentService enrollmentService, JWTUtil jwtUtil) {
        this.classroomService = classroomService;
        this.opinionService = opinionService;
        this.enrollmentService = enrollmentService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/classroomMakePin/{classId}/{classNumber}")
    public ApiResponse classroomMakePin(@PathVariable UUID classId, @PathVariable String classNumber) {
        classroomService.createPinMapping(classNumber, classId);
        return new ApiResponse("클래스룸 pin번호 발급완료");
    }

    //학생 or 교수 특정 수업 입장
    @GetMapping("/classroomEnter/pin/{classNumber}")
    public ClassroomResultDTO classroomEnter(@PathVariable String classNumber, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);
        String role = jwtUtil.getRole(jwtToken);

        UUID classId = classroomService.getMapping().get(classNumber);
        Classroom classroom = classroomService.getClassroomById(classId).orElseThrow();
        List<Opinion> opinions = opinionService.findOpinionId(classId);
        //학생일경우 수강목록에 저장
        if (role.equals("student")) {
            enrollmentService.createEnroll(classroom,email);
        }
        return new ClassroomResultDTO(classroom, opinions);
    }


    @PostMapping("/classroomEnter/{classId}")
    public ClassroomResultDTO classroomEnter(@PathVariable UUID classId,@RequestHeader("Authorization") String token) {
        Classroom classroom = classroomService.getClassroomById(classId).orElseThrow();
        List<Opinion> opinions = opinionService.findOpinionId(classId);
        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);
        String role = jwtUtil.getRole(jwtToken);
        //학생일경우 수강목록에 저장
        if (role.equals("student")) {
            enrollmentService.createEnroll(classroom,email);
        }
        return new ClassroomResultDTO(classroom, opinions);
    }

    @PostMapping
    public ClassroomResultDTO createClassroomAndQuiz(@RequestBody ClassroomDTO classroomDTO, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);

        Classroom classroom = classroomService.createClassroom(classroomDTO, email);
        List<Opinion> ops = opinionService.createOrUpdateOpinion(classroom, classroomDTO);
        return new ClassroomResultDTO(classroom, ops);
    }

    @PutMapping("/classroom/update")
    public ResultUpdateClassroomDTO updateOpinions(@RequestBody UpdateClassroomDTO updateClassroomDTO, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String email = jwtUtil.getEmail(jwtToken);
        Classroom classroom = classroomService.updateClassroom(updateClassroomDTO.getClassroom());
        List<Opinion> opinionList = opinionService.UpdateOpinion(updateClassroomDTO, email, classroom);
        return new ResultUpdateClassroomDTO(classroom,opinionList);
    }

    @DeleteMapping("/classDelete/{id}")
    public ApiResponse deleteClassroom(@PathVariable UUID id) {
        classroomService.deleteClassroom(id);
        return new ApiResponse("수업 삭제 성공");
    }

    @GetMapping("/opinionSelect/{classId}")
    public List<Opinion> getOpinion(@PathVariable UUID classId) {
        return opinionService.findOpinionId(classId);
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
