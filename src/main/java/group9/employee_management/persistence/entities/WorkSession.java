package group9.employee_management.persistence.entities;
import javax.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
public class WorkSession {

    @Id
    private Date date;

    // The timestamps from where the user started working to when he ended working.
    private Date startTime;
    private Date stopTime;

    // The text description given by the user about this work-session.
    //TODO multiple messages?
    private String textStatus;

    // Describes whether a user is currently available during this work-session.
    private boolean available;

    // Describes whether a user is currently on-site during this work-session.
    private boolean onSite;

    public WorkSession(){}

    public WorkSession(Date date, Date startTime, Date stopTime,  String textStatus, boolean available, boolean onSite) {
        super();
        this.date = date;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.textStatus = textStatus;
        this.available = available;
        this.onSite = onSite;
    }
}
