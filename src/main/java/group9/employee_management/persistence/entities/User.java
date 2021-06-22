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
@Table
public class User {

    // Employee or admin name.
    @Column
    private String firstName;
    @Column
    private String lastName;

    @Id
    @Column(name = "user_username")
    private String userName;
    @Column
    private String password;

    // Initially always true;
    @Column
    private boolean isFirstLogin;
    @Column
    private boolean isAdmin;

    // Describes the corporate title of an user.
    @Column
    private String position;

    // Describes how long this account is valid;
    @Column
    private Date validity;


    //TODO this produces an Error
    // The work-sessions (work days) associated with this user.

    @OneToMany(mappedBy = "user")
    private Set<WorkSession> workSessions;

    public User(){}

    public User (String userName, String firstName, String lastName, String password, boolean isAdmin, String position, Date validity, Set<WorkSession> workSessions){
        super();
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isAdmin = isAdmin;
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

    public void setPosition(String position) {
        this.position = position;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getPosition() {
        return position;
    }

    public Date getValidity() {
        return validity;
    }
}

