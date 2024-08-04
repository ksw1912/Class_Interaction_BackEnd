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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final InstructorRepository instructorRepository;
    private final ScheduledExecutorService scheduledExecutorService;
    private final ConcurrentHashMap<String, UUID> pinMapping = new ConcurrentHashMap<>();

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository, InstructorRepository instructorRepository, ScheduledExecutorService scheduledExecutorService) {
        this.classroomRepository = classroomRepository;
        this.instructorRepository = instructorRepository;
        this.scheduledExecutorService = scheduledExecutorService;
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

    @Transactional
    public Classroom updateClassroom(Classroom classroom) {
        UUID classId = classroom.getClassId();
        if (classroomRepository.existsByClassId(classId)) {
            // 기존 classroom을 데이터베이스에서 조회
            Classroom existingClassroom = classroomRepository.findById(classId)
                    .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

            existingClassroom.setClassName(classroom.getClassName());


            return classroomRepository.save(existingClassroom);
        }
        throw new IllegalArgumentException("Classroom does not exist");
    }


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

    public void createPinMapping(String classNumber, UUID classId) {
        pinMapping.put(classNumber, classId);
        scheduledExecutorService.schedule(() -> {
            pinMapping.remove(classNumber);
            scheduledExecutorService.shutdown();
        }, 30, TimeUnit.MINUTES);
    }

    public ConcurrentHashMap<String, UUID> getMapping() {
        return pinMapping;
    }
}
