package group9.employee_management.application.service;

import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.UserRepository;
import group9.employee_management.persistence.repositories.WorkSessionRepository;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkSessionService(WorkSessionRepository workSessionRepository, UserRepository userRepository) {
        this.workSessionRepository = workSessionRepository;
        this.userRepository = userRepository;
    }

    /*
    Getters for the work-session history.
     */
    public WorkSession getLatest(String userName) {
        return workSessionRepository.getWorkSession(userName, getIndex(userName));
    }

    /**
     * Acquires the highest index of a work-session of this user.
     *
     * @param userName The user-name.
     * @return The highest index.
     */
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

    /*
    Setters for the work-session creation.
     */

    /**
     * Acquires the current time.
     *
     * @return The current time.
     */
    private static Date getCurrentTime() {
        LocalDate now = LocalDate.now();
        return Date.valueOf(now);
    }

    /**
     * Creates a new {@code WorkSession} entity in the database. The index is automatically set to be the highest for
     * that user. The {@code starTime} is calculated at the moment of the method call. The {@code stopTime} is set to
     * null.
     *
     * @param userName The user-name of the user with which this session is associated.
     * @param textStatus The text-message describing this session.
     * @param available The momentary availabilty of the employee.
     * @param onSite Indication for whether this employee is on- or offsite.
     */
    public void startSession(String userName,String textStatus, boolean available, boolean onSite) {
        Date currentTime = getCurrentTime();

        WorkSession newSession = new WorkSession(getIndex(userName) + 1, currentTime, null, textStatus,
                available, onSite, userRepository.getUserByUserName(userName));
        workSessionRepository.save(newSession);
    }

    /**
     * Sets the {@code stopTime} of an users latest session to the current time.
     *
     * @param userName The users user-name.
     */
    public void stopSession(String userName) {
        WorkSession latestSession = workSessionRepository.getWorkSession(userName, getIndex(userName));
        latestSession.setStopTime(getCurrentTime());
        workSessionRepository.save(latestSession);
    }

}
