package group9.employee_management.application.service;

import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.WorkSessionRepository;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;

    @Autowired
    public WorkSessionService(WorkSessionRepository workSessionRepository) {
        this.workSessionRepository = workSessionRepository;
    }

    public WorkSession getLatest(String userName) {
        return workSessionRepository.getWorkSession(userName, getIndex(userName));
    }

    public int getIndex(String userName) {
        return workSessionRepository.getIndex(userName);
    }

    public List<WorkSession> getFiveFromIndex(String userName, int index) {
        List<WorkSession> workSessions = new ArrayList<>();
        for (int i = index; i > index - 5 && i >= 0; i--) {
            WorkSession session = workSessionRepository.getWorkSession(userName, i);
            assert session != null;
            workSessions.add(session);
        }
        return workSessions;
    }
}
