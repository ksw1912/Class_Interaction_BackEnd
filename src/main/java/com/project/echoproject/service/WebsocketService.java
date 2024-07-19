package com.project.echoproject.service;

import com.project.echoproject.dto.websocketDTO.ClassDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebsocketService {
    private Map<UUID, ClassDTO> rooms = new ConcurrentHashMap<>();

    public boolean roomExists(UUID classId) {
        return rooms.containsKey(classId);
    }

    public void createRoom(UUID classId) {
        rooms.putIfAbsent(classId, new ClassDTO(classId, true));
    }

    public void addUserEmail(UUID classId, String email) {
        rooms.get(classId).getUserEmails().add(email);
    }

    public void removeUserEmailFromRoom(UUID classId, String email) {
        ClassDTO classDTO = rooms.get(classId);
        if (classDTO != null) {
            Set<String> userEmails = classDTO.getUserEmails();
            userEmails.remove("email");
            classDTO.setUserEmails(userEmails);
            if (classDTO.getUserEmails().isEmpty()) {
                rooms.remove(classId);
            }
        }
    }


}
