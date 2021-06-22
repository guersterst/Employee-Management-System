package group9.employee_management.persistence.entities;
import javax.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table
public class WorkSession {

    @Id
    @Column
    private Integer id;

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

    public WorkSession(Integer id, Date startTime, Date stopTime,  String textStatus, boolean available, boolean onSite,
                       User user) {
        super();
        this.id = id;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.textStatus = textStatus;
        this.available = available;
        this.onSite = onSite;
        this.user = user;
    }
}
