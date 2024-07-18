package com.project.echoproject.service;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Instructor;
import com.project.echoproject.dto.classroom.ClassroomDTO;
import com.project.echoproject.repository.ClassroomRepository;
import com.project.echoproject.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository, InstructorRepository instructorRepository) {
        this.classroomRepository = classroomRepository;
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    public Classroom createClassroom(ClassroomDTO classroomDTO, String email) {
        String className = classroomDTO.getClassName();
        if (isClassNameDuplicate(className)) {
            System.out.println("classroomDB 중복");
            throw new IllegalArgumentException("클래스룸이 이미 존재합니다.");
        }
        // Classroom 객체 생성 및 저장
        Instructor instructor = instructorRepository.findByEmail(email);
        Classroom classroom = new Classroom();
        classroom.setClassName(className);
        classroom.setInstructor(instructor);
        return classroomRepository.save(classroom);
    }

    // 특정 교수의 이메일로 클래스룸 목록 가져오기
    @Transactional(readOnly = true)
    public List<Classroom> getClassroomsByInstructorEmail(String email) {
        return classroomRepository.findByInstructorEmail(email);
    }


    public Optional<Classroom> getClassroomByClassName(String className) {
        return classroomRepository.findByClassName(className);
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    //정확 X
//    @Transactional
//    public Classroom updateClassroom(Long id, Classroom updatedClassroom) {
//        return classroomRepository.findById(id)
//                .map(classroom -> {
//                    classroom.setClassName(updatedClassroom.getClassName());
//                    classroom.setProfessorName(updatedClassroom.getProfessorName());
//                    classroom.setProfessor(updatedClassroom.getProfessor());
//                    return classroomRepository.save(classroom);
//                })
//                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
//    }

    @Transactional
    public void deleteClassroom(UUID id) {
        classroomRepository.deleteById(id);
    }

    //테이블내에 className이 존재 여부
    public boolean isClassNameDuplicate(String className) {
        return classroomRepository.existsByClassName(className);
    }

    public boolean isClassIdDuplicate(UUID classId) {
        return classroomRepository.existsByClassId(classId);
    }

    @Transactional(readOnly = true)
    public Optional<Classroom> getClassroomById(UUID classId) {
        return classroomRepository.findById(classId);
    }
}
