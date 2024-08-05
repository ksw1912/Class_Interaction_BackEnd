package com.project.echoproject.service;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Opinion;
import com.project.echoproject.dto.classroom.ClassroomDTO;
import com.project.echoproject.dto.classroom.UpdateClassroomDTO;
import com.project.echoproject.repository.ClassroomRepository;
import com.project.echoproject.repository.OpinionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpinionService {
    private OpinionRepository opinionRepository;
    public OpinionService(OpinionRepository opinionRepository) {
        this.opinionRepository = opinionRepository;
    }

    @Transactional
    public List<Opinion> createOrUpdateOpinion(Classroom classroom, ClassroomDTO classroomDTO) {
        UUID classId = classroom.getClassId();
        List<String> opinions = classroomDTO.getOps();

        if (opinionRepository.existsByClassroomClassId(classId)) {
            opinionRepository.deleteByClassroomClassId(classId);
        }

        List<Opinion> opinionList = opinions.stream()
                .map(ops -> {
                    Opinion op = new Opinion();
                    op.setClassroom(classroom);
                    op.setOpinion(ops);
                    return opinionRepository.save(op);
                })
                .collect(Collectors.toList());
        return opinionList;
    }

    @Transactional
    public List<Opinion> UpdateOpinion(UpdateClassroomDTO updateClassroomDTO, String email, Classroom room) {
        UUID classId = updateClassroomDTO.getClassroom().getClassId();
        List<String> opList = updateClassroomDTO.getOpinion();
        List<Opinion> opinionList = new ArrayList<>();

        if (opinionRepository.existsByClassroomClassId(classId)) {
            opinionRepository.deleteByClassroomClassId(classId);
        }

        for (var ops : opList) {
            if (ops == null || ops.isEmpty()) {
                continue;
            }
            Opinion op = new Opinion();
            op.setOpinion(ops);
            op.setClassroom(room);
            opinionList.add(opinionRepository.save(op));
        }
        return opinionList;

    }

    @Transactional(readOnly = true)
    public List<Opinion> findOpinionId(UUID classId) {
        return opinionRepository.findByClassroomClassId(classId);
    }

}


