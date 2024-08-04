package com.project.echoproject.service;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Enrollment;
import com.project.echoproject.domain.Student;
import com.project.echoproject.repository.EnrollmentRepository;
import com.project.echoproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
    }
    public void createEnroll(Classroom classroom,String email){
        if(!duplicateEnrollClassroom(classroom.getClassId())){
            Student student = studentRepository.findByEmail(email);
            Enrollment enrollment = new Enrollment();
            enrollment.setClassroom(classroom);
            enrollment.setStudent(student);
            enrollmentRepository.save(enrollment);
        }
    }

    public boolean duplicateEnrollClassroom(UUID classId){
        return enrollmentRepository.existsByClassroomClassId(classId);
    }

    @Transactional
    public void deleteEnrollment(UUID id) {
        enrollmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Enrollment findByEnrollmentClassId(Classroom classroom){
        Enrollment enrollment = enrollmentRepository.findByClassroomClassId(classroom.getClassId()).orElseThrow();
        return enrollment;
    }

}