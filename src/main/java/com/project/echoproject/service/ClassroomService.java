package com.project.echoproject.service;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Transactional
    public Classroom createClassroom(Classroom classroom) {
        if (isClassNameDuplicate(classroom.getClassName())) {
            throw new IllegalArgumentException("Class name already exists");
        }
        return classroomRepository.save(classroom);
    }

    public Optional<Classroom> getClassroomById(Long id) {
        return classroomRepository.findById(id);
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
    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }

    //테이블내에 className이 존재 여부
    public boolean isClassNameDuplicate(String className) {
        return classroomRepository.existsByClassName(className);
    }
}
