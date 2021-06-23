package group9.employee_management.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.UserRepository;
import group9.employee_management.persistence.repositories.WorkSessionRepository;
import group9.employee_management.web.dto.WorkSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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

    public String workSessionsToJSON(List<WorkSession> sessions) throws JsonProcessingException {
        StringBuilder jsonArrayResponse = new StringBuilder("{ \"workSessions\": [");

        for (WorkSession workSession : sessions) {
            if (workSession != null) {
                jsonArrayResponse.append(WorkSessionDTO.fromEntity(workSession).toJSON());
                jsonArrayResponse.append(", ");
            }
        }
        String result = jsonArrayResponse.toString();

        // Remove last comma if there is an element in the json array.
        if (jsonArrayResponse.length() > 20) {
            result = jsonArrayResponse.substring(0, jsonArrayResponse.length() - 2);
        }

        return result + "]}";
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

    public List<WorkSession> getThreeFromIndex(String userName, int index) {
        List<WorkSession> workSessions = new ArrayList<>();
        for (int i = index; i > index - 3 && i >= 0; i--) {
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
     * @param userName   The user-name of the user with which this session is associated.
     * @param textStatus The text-message describing this session.
     * @param available  The momentary availabilty of the employee.
     * @param onSite     Indication for whether this employee is on- or offsite.
     */
    public void startSession(String userName, String textStatus, boolean available, boolean onSite) {
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
        WorkSession latestSession = getLatest(userName);
        latestSession.setStopTime(getCurrentTime());

        workSessionRepository.save(latestSession);
    }

    public void putMessage(String userName, String textStatus) {
        WorkSession latestSession = getLatest(userName);
        latestSession.setTextStatus(textStatus);
        workSessionRepository.save(latestSession);
    }

    public void putAvailability(String userName, boolean available) {
        WorkSession latestSession = getLatest(userName);
        latestSession.setAvailable(available);
        workSessionRepository.save(latestSession);
    }

    public void putOnSite(String userName, boolean onSite) {
        WorkSession latestSession = getLatest(userName);
        latestSession.setOnSite(onSite);
        workSessionRepository.save(latestSession);
    }


    public String getTextStatus(String userName) {
        return workSessionRepository.getTextStatus(userName);
    }

    public String getAvailability(String userName) {
        return workSessionRepository.getAvailability(userName);
    }

    public String getOnSite(String userName) {
        return workSessionRepository.getOnSite(userName);
    }

    /*
    Getter for session history.
     */

    /*
    Getters for employee-list-view.
     */
    public User getUser(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    public String getUsersWithRunningSessionsAsJSON() {
        List<User> usersWithRunningSessions = workSessionRepository.getUsersWithRunningSessions();
        StringBuilder jsonResponse = new StringBuilder("[");

        for (User user : usersWithRunningSessions) {
            jsonResponse.append("\"").append(user.getUserName()).append("\"").append(", ");
        }

        String result = jsonResponse.toString();

        // Remove following comma if necessary.
        if (jsonResponse.length() > 2) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }
}
