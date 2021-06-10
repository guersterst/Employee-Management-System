package group9.employee_management.persistence.entities;
import javax.persistence.*;

import java.sql.Date;

@Entity
public class WorkSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    // The timestamps from where the user started working to when he ended working.
    private Date startTime;
    private Date stopTime;

    // The text description given by the user about this work session.
    private String textStatus;

    public WorkSession(){}

    public WorkSession(Date startTime, Date stopTime, String textStatus) {
        super();
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.textStatus = textStatus;
    }

    //TODO
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user0_.id")
    private User user;
     */

}
