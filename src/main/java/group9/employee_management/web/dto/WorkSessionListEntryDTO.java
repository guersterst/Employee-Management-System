package group9.employee_management.web.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group9.employee_management.persistence.entities.Employee;
import group9.employee_management.persistence.entities.WorkSession;

import java.sql.Date;
import java.sql.Timestamp;

public class WorkSessionListEntryDTO {

    private String firstName;
    private String lastName;
    private String userName;
    private Timestamp startTime;
    private Timestamp stopTime;
    private String textStatus;
    private boolean available;
    private boolean onSite;
    private double latitude;
    private double longitude;

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
        workSessionListEntryDTO.setLongitude(workSession.getLongitude());
        workSessionListEntryDTO.setLatitude(workSession.getLatitude());
        return workSessionListEntryDTO;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getStopTime() {
        return stopTime;
    }

    public void setStopTime(Timestamp stopTime) {
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
