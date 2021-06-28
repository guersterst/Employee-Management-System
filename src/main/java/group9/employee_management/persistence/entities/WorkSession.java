package group9.employee_management.persistence.entities;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.sql.Date;

@Entity
@Table
public class WorkSession {

    @Id
    @Column
    private Integer index;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="user_username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    // The timestamps from where the user started working to when he ended working.
    @Column
    private Date startTime;
    @Column
    private Date stopTime;

    // The text description given by the user about this work-session.
    @Column
    private String textStatus;

    // Describes whether a user is currently available during this work-session.
    @Column
    private boolean available;

    // Describes whether a user is currently on-site during this work-session.
    @Column
    private boolean onSite;



    public WorkSession(){}

    public WorkSession(Integer index, Date startTime, Date stopTime, String textStatus, boolean available, boolean onSite,
                       Employee employee) {
        super();
        this.index = index;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.textStatus = textStatus;
        this.available = available;
        this.onSite = onSite;
        this.employee = employee;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setOnSite(boolean onSite) {
        this.onSite = onSite;
    }
}
