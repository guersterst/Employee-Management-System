package group9.employee_management.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import group9.employee_management.application.exception.NoSessionsException;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.persistence.entities.Employee;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.EmployeeRepository;
import group9.employee_management.persistence.repositories.WorkSessionRepository;
import group9.employee_management.web.dto.UserDTO;
import group9.employee_management.web.dto.WorkSessionDTO;
import group9.employee_management.web.dto.WorkSessionListEntryDTO;
import org.hibernate.boot.spi.XmlMappingBinderAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public WorkSessionService(WorkSessionRepository workSessionRepository, EmployeeRepository employeeRepository) {
        this.workSessionRepository = workSessionRepository;
        this.employeeRepository = employeeRepository;
    }

    /*
    Getters for the work-session history.
     */
    public WorkSession getLatest(String userName) {
        hasLatestSession(userName);
        if (workSessionRepository.getWorkSession(userName, getIndex(userName)) != null) {
            return workSessionRepository.getWorkSession(userName, getIndex(userName));
        } else {
            throw new NoSessionsException(userName);
        }
    }

    /**
     * Acquires the highest index of a work-session of this user.
     *
     * @param userName The user-name.
     * @return The highest index.
     */
    public int getIndex(String userName) {
        isEmployee(userName);
        if (workSessionRepository.getIndex(userName) != null) {
            return workSessionRepository.getIndex(userName);
        } else {
            return 0;
        }
    }

    public List<WorkSession> getThreeFromIndex(String userName, int index) {
        isEmployee(userName);
        List<WorkSession> workSessions = new ArrayList<>();
        for (int i = index; i > index - 3; i--) {
            WorkSession session = workSessionRepository.getWorkSession(userName, i);
            workSessions.add(session);
        }
        return workSessions;
    }

    public WorkSession getOneFromIndex(String userName, int index) {
        hasLatestSession(userName);
        if (workSessionRepository.getWorkSession(userName, index) != null) {
            return workSessionRepository.getWorkSession(userName, index);
        } else {
            throw new NoSessionsException(userName);
        }
    }

    /*
    Setters for the work-session creation.
     */

    /**
     * Acquires the current time.
     *
     * @return The current time.
     */
    private static Timestamp getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
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
        isEmployee(userName);
        Timestamp currentTime = getCurrentTime();
        int offset = 1;
        if (workSessionRepository.getWorkSession(userName, 0) == null) {

            // If this user has no sessions yet the index should start at 0;
            offset = 0;
        }
        WorkSession newSession = new WorkSession(getIndex(userName) + offset, currentTime, null, textStatus,
                available, onSite, employeeRepository.getUserByUserName(userName));
        workSessionRepository.save(newSession);
    }

    /**
     * Sets the {@code stopTime} of an users latest session to the current time.
     *
     * @param userName The users user-name.
     */
    public void stopSession(String userName) {
        hasLatestSession(userName);
        WorkSession latestSession = getLatest(userName);
        latestSession.setStopTime(getCurrentTime());
        latestSession.setOnSite(false);
        workSessionRepository.save(latestSession);
    }

    public void deleteSession(String userName, int index) {
        WorkSession session = workSessionRepository.getWorkSession(userName, index);
        if (session == null) {
            throw new NoSessionsException(userName);
        } else {
            workSessionRepository.delete(session);
        }
    }

    public void putMessage(String userName, String textStatus) {
        hasLatestSession(userName);
        WorkSession latestSession = getLatest(userName);
        latestSession.setTextStatus(textStatus);
        workSessionRepository.save(latestSession);
    }

    public void putAvailability(String userName, boolean available) {
        hasLatestSession(userName);
        WorkSession latestSession = getLatest(userName);
        latestSession.setAvailable(available);
        workSessionRepository.save(latestSession);
    }

    public void putOnSite(String userName, boolean onSite) {
        hasLatestSession(userName);
        WorkSession latestSession = getLatest(userName);
        latestSession.setOnSite(onSite);
        workSessionRepository.save(latestSession);
    }


    public String getTextStatus(String userName) {
        hasLatestSession(userName);
        return workSessionRepository.getTextStatus(userName, getIndex(userName));
    }

    public void deleteTextStatus(String userName) {
        hasLatestSession(userName);
        WorkSession session = workSessionRepository.getWorkSession(userName, workSessionRepository.getIndex(userName));
        session.setTextStatus("");
        workSessionRepository.save(session);
    }

    public boolean getAvailability(String userName) {
        hasLatestSession(userName);
        return workSessionRepository.getAvailability(userName);
    }

    public boolean getOnSite(String userName) {
        hasLatestSession(userName);
        return workSessionRepository.getOnSite(userName);
    }

    /*
    Getter for session history.
     */

    public List<WorkSessionDTO> getSessions(String userName) {
        hasLatestSession(userName);
        List<WorkSessionDTO> result = new ArrayList<>();

        for (int i = workSessionRepository.getIndex(userName); i >= 0; i--) {
            result.add(WorkSessionDTO.fromEntity(workSessionRepository.getWorkSession(userName, i)));
        }
        return result;
    }

    /*
    Getters for employee-list-view.
     */
    public Employee getUser(String userName) {
        hasLatestSession(userName);
        return employeeRepository.getUserByUserName(userName);
    }

    public String getUsersWithRunningSessionsAsJSON() {
        List<Employee> usersWithRunningSessions = workSessionRepository.getEmployeesWithRunningSessions();

        // Build JSON.
        StringBuilder jsonResponse = new StringBuilder("[");
        for (Employee employee : usersWithRunningSessions) {
            jsonResponse.append("\"").append(employee.getUserName()).append("\"").append(", ");
        }
        String result = jsonResponse.toString();

        // Remove following comma if necessary.
        if (jsonResponse.length() > 1) {
            result = result.substring(0, result.length() - 2);
        }
        return result + "]";
    }

    public List<UserDTO> getLatestSessionsOfAllEmployees() {
        List<Employee> employees = workSessionRepository.getAllEmployeesWithSessions();
        List<UserDTO> result = new ArrayList<>();
        for (Employee employee : employees) {
            result.add(UserDTO.fromEntity(employee));
        }
        return result;
    }

    public List<WorkSessionListEntryDTO> getListEntries() {
        List<UserDTO> users = getLatestSessionsOfAllEmployees();
        List<WorkSessionListEntryDTO> result = new ArrayList<>();
        for (UserDTO user : users) {
            result.add(WorkSessionListEntryDTO.fromEntities(getUser(user.getUserName()),
                    getLatest(user.getUserName())));
        }
        return result;
    }

    /*
    CSV and JSOn converters.
     */

    public String workSessionsToJSON(String userName) {
        hasLatestSession(userName);
        return new Gson().toJson(getSessions(userName));

    }

    public String workSessionsToXML(String userName) throws IOException {
        List<WorkSessionDTO> sessions = getSessions(userName);
        JsonNode node = new ObjectMapper().readTree(workSessionsToJSON(userName));

        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(sessions);
    }


    /*
    For defensive programming.
     */

    public void hasLatestSession(String userName) {
        if (workSessionRepository.getEmployeeByUserName(userName) == null) {
            throw new NoSuchUserException(userName);
        } else if (workSessionRepository.getWorkSession(userName, workSessionRepository.getIndex(userName)) == null) {
            throw new NoSessionsException(userName);
        }
    }

    private void isEmployee(String userName) {
        if (workSessionRepository.getEmployeeByUserName(userName) == null)
            throw new NoSuchUserException(userName);
    }

    /*
     Not in use anymore.
     */

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
}