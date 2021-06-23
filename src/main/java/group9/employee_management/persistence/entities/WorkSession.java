package group9.employee_management.persistence.entities;
import javax.persistence.*;

import java.sql.Date;

@Entity
@Table
public class WorkSession {

    @Id
    @Column
    private Integer index;

    // The timestamps from where the user started working to when he ended working.
    @Column
    private Date startTime;
    @Column
    private Date stopTime;

    // The text description given by the user about this work-session.
    //TODO multiple messages?
    @Column
    private String textStatus;

    // Describes whether a user is currently available during this work-session.
    @Column
    private boolean available;

    // Describes whether a user is currently on-site during this work-session.
    @Column
    private boolean onSite;

    @ManyToOne
    @JoinColumn(name ="user_username", nullable = false)
    private User user;


    public WorkSession(){}

    public WorkSession(Integer index, Date startTime, Date stopTime, String textStatus, boolean available, boolean onSite,
                       User user) {
        super();
        this.index = index;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.textStatus = textStatus;
        this.available = available;
        this.onSite = onSite;
        this.user = user;
    }

    public Integer getIndex() {
        return index;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public String getTextStatus() {
        return textStatus;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isOnSite() {
        return onSite;
    }

    public User getUser() {
        return user;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }
}
