package group9.employee_management.web.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group9.employee_management.persistence.entities.Employee;
import group9.employee_management.persistence.entities.WorkSession;

import java.sql.Date;

public class WorkSessionListEntryDTO {

    private String firstName;
    private String lastName;
    private String userName;
    private Date startTime;
    private Date stopTime;
    private String textStatus;
    private boolean available;
    private boolean onSite;
    private Integer coordX;
    private Integer coordY;

    public WorkSessionListEntryDTO(){}

    public static WorkSessionListEntryDTO fromEntities(Employee employee, WorkSession workSession) {
        WorkSessionListEntryDTO workSessionListEntryDTO = new WorkSessionListEntryDTO();
        workSessionListEntryDTO.setFirstName(employee.getFirstName());
        workSessionListEntryDTO.setLastName(employee.getLastName());
        workSessionListEntryDTO.setUserName(employee.getUserName());
        workSessionListEntryDTO.setStartTime(workSession.getStartTime());
        workSessionListEntryDTO.setStopTime(workSession.getStopTime());
        workSessionListEntryDTO.setTextStatus(workSession.getTextStatus());
        workSessionListEntryDTO.setAvailable(workSession.isAvailable());
        workSessionListEntryDTO.setOnSite(workSession.isOnSite());
        return workSessionListEntryDTO;
    }

    public String toJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isOnSite() {
        return onSite;
    }

    public void setOnSite(boolean onSite) {
        this.onSite = onSite;
    }
}
