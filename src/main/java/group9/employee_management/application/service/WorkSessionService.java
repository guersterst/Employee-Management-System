package group9.employee_management.application.service;

import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.WorkSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;

    @Autowired
    public WorkSessionService(WorkSessionRepository workSessionRepository) {
        this.workSessionRepository = workSessionRepository;
    }

    public WorkSession getLatest(String userName) {
        return null;
    }
}
