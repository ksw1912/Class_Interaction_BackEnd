package com.project.echoproject.service;

import com.project.echoproject.dto.websocketDTO.ClassDTO;
import com.project.echoproject.dto.websocketDTO.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebsocketService {

    private Map<UUID, MessageDTO> rooms = new ConcurrentHashMap<>();

    public Map<UUID, MessageDTO> getRooms() {
        return rooms;
    }

    public boolean roomExists(UUID classId) {
        return rooms.containsKey(classId);
    }

    public void createRoom(UUID classId) {
        rooms.putIfAbsent(classId, new MessageDTO(MessageDTO.Status.OPEN, null, null, null, null, 0, classId, true));
    }

    public void closeRoom(UUID classId) {
        rooms.remove(classId);
    }

    public void addUserEmail(UUID classId, String email) {
        if (rooms.get(classId) != null) {
            rooms.get(classId).getUserEmails().add(email);
        }
    }

    public void removeUserEmailFromRoom(UUID classId, String email) {
        MessageDTO MessageDTO = rooms.get(classId);
        if (MessageDTO != null) {
            Set<String> userEmails = MessageDTO.getUserEmails();
            userEmails.remove(email);
            MessageDTO.setUserEmails(userEmails);
            if (MessageDTO.getUserEmails().isEmpty()) {
                rooms.remove(classId);
            }
        }
    }
}
