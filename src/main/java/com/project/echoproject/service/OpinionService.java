package com.project.echoproject.service;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Opinion;
import com.project.echoproject.dto.classroom.ClassroomDTO;
import com.project.echoproject.dto.classroom.UpdateClassroomDTO;
import com.project.echoproject.repository.ClassroomRepository;
import com.project.echoproject.repository.OpinionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public List<Opinion> UpdateOpinion(UpdateClassroomDTO updateClassroomDTO, String email,Classroom room){
        UUID classId = updateClassroomDTO.getClassroom().getClassId();
        List<String> opList = updateClassroomDTO.getOpinion();

        if (opinionRepository.existsByClassroomClassId(classId)) {
            opinionRepository.deleteByClassroomClassId(classId);
        }
        return opList.stream()
                .map(ops -> {
                    Opinion op = new Opinion();
                    op.setClassroom(room);
                    op.setOpinion(ops);
                    return opinionRepository.save(op);
                })
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<Opinion> findOpinionId(UUID classId){
        return opinionRepository.findByClassroomClassId(classId);
    }

}


