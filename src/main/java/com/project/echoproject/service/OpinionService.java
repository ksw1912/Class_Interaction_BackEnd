package com.project.echoproject.service;

import com.project.echoproject.domain.Classroom;
import com.project.echoproject.domain.Opinion;
import com.project.echoproject.dto.classroom.ClassroomDTO;
import com.project.echoproject.repository.OpinionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpinionService {
    private OpinionRepository opinionRepository;

    public OpinionService(OpinionRepository opinionRepository) {
        this.opinionRepository = opinionRepository;
    }

    @Transactional
    public List<Opinion> createOpinion(Classroom classroom, ClassroomDTO classroomDTO) {
        List<String> opinions = classroomDTO.getOps();

        return opinions.stream()
                .filter(opinionText -> {
                    if (opinionText == null) {
                        throw new IllegalArgumentException("의견 존재하지 않음");
                    }
                    if (isOpinionDuplicate(opinionText)) {
                        throw new IllegalArgumentException("의견 중복");
                    }
                    return true;
                })
                .map(opinionText -> {
                    Opinion op = new Opinion();
                    op.setClassroom(classroom);
                    op.setOpinion(opinionText);
                    return opinionRepository.save(op);
                })
                .collect(Collectors.toList());
    }

    public boolean isOpinionDuplicate(String opinion) {
        return opinionRepository.existsByOpinion(opinion);
    }
}


