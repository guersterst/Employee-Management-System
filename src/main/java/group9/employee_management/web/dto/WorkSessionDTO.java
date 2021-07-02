package group9.employee_management.web.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group9.employee_management.persistence.entities.WorkSession;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class WorkSessionDTO {

    private Integer id;
    private Timestamp startTime;
    private Timestamp stopTime;
    private String textStatus;
    private boolean available;
    private boolean onSite;
    private String userName;
    private double latitude;
    private double longitude;

    public WorkSessionDTO(){}

    /**
     * Converts a WorkSession entity into an DTO.
     *
     * @param workSession The entity to be converted.
     * @return The DTO created from the entity
     */
    public static WorkSessionDTO fromEntity(WorkSession workSession) {
        WorkSessionDTO workSessionDTO = new WorkSessionDTO();
        workSessionDTO.setId(workSession.getIndex());
        workSessionDTO.setStartTime(workSession.getStartTime());
        workSessionDTO.setStopTime(workSession.getStopTime());
        workSessionDTO.setTextStatus(workSession.getTextStatus());
        workSessionDTO.setAvailable(workSession.isAvailable());
        workSessionDTO.setOnSite(workSession.isOnSite());
        workSessionDTO.setUserName(workSession.getEmployee().getUserName());
        workSessionDTO.setLongitude(workSession.getLongitude());
        workSessionDTO.setLatitude(workSession.getLatitude());
        return workSessionDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
