package group9.employee_management.persistence.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.jdbc.Work;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * An Entity describing a user of the terminal.
 */
@Entity
public class User {



    // Employee or admin name.
    private String firstName;
    private String lastName;

    @Id
    private String userName;
    private String password;

    // Initially always true;
    private boolean isFirstLogin;
    private boolean isAdmin;

    // True indicates that this user is currently working, while false indicates the opposite.
    private boolean isWorking;

    // Describes the corporate title of an user.
    private String position;

    // Describes how long this account is valid;
    // TODO not sure about datatype. Has too many deprecated methods.
    private Date validity;

    // The work sessions (work days) associated with this user.
    @OneToMany(mappedBy = "user")
    private Set<WorkSession> workSessions;

    public User(){}

    public User (String userName, String firstName, String lastName, String password, boolean isAdmin,
                 boolean isWorking, String position, Date validity, Set<WorkSession> workSessions){
        super();
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isWorking = isWorking;
        this.position = position;
        this.validity = validity;
        this.isFirstLogin = true;
        this.workSessions = workSessions;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstLogin(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }
}

