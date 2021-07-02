package group9.employee_management.persistence.entities;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table
public class WorkSession {

    @EmbeddedId
    private WorkSessionID id;

    @Embeddable
    static
    class WorkSessionID implements Serializable {
        Integer index;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "user_username" ))
        @OnDelete(action = OnDeleteAction.CASCADE)
        Employee employee;

        public WorkSessionID(Integer index, Employee employee) {
            this.index = index;
            this.employee = employee;
        }


        public WorkSessionID() {
        }
    }

    // The timestamps from where the user started working to when he ended working.
    @Column
    private Timestamp startTime;
    @Column
    private Timestamp stopTime;

    // The text description given by the user about this work-session.
    @Column
    private String textStatus;

    // Describes whether a user is currently available during this work-session.
    @Column
    private boolean available;

    // Describes whether a user is currently on-site during this work-session.
    @Column
    private boolean onSite;

    @Column
    private Integer coordX;

    @Column
    private Integer coordY;



    public WorkSession(){}

    public WorkSession(Integer index, Timestamp startTime, Timestamp stopTime, String textStatus, boolean available,
                       boolean onSite,
                       Employee employee) {
        super();
        this.id = new WorkSessionID(index, employee);
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.textStatus = textStatus;
        this.available = available;
        this.onSite = onSite;
    }

    public Integer getIndex() {
        return id.index;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getStopTime() {
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
        return id.employee;
    }

    public void setStopTime(Timestamp stopTime) {
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

    public Integer getCoordY() {
        return coordY;
    }

    public void setCoordY(Integer coordY) {
        this.coordY = coordY;
    }

    public Integer getCoordX() {
        return coordX;
    }

    public void setCoordX(Integer coordX) {
        this.coordX = coordX;
    }
}
