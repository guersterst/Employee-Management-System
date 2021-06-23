package group9.employee_management.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group9.employee_management.persistence.entities.WorkSession;

import java.sql.Date;

public class WorkSessionDTO {

    @JsonProperty
    private Integer id;
    private Date startTime;
    private Date stopTime;
    private String textStatus;
    private boolean available;
    private boolean onSite;
    private String userName;

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
        workSessionDTO.setUserName(workSession.getUser().getUserName());
        return workSessionDTO;
    }

    public String toJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
