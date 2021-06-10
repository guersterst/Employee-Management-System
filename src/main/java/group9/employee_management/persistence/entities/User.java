package group9.employee_management.persistence.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * An Entity describing a user of the terminal.
 */
@Entity
public class User {

    @Id
    //@Column(name = "user_id")
    //@GeneratedValue(strategy = GenerationType.AUTO) //Best practice?
    private String id;

    // Employee or admin name.
    private String name;

    //TODO: password security.
    private String password;

    // Initially always true;
    private boolean isFirstLogin;

    // One-time password for the initial login.
    private String token;
    private boolean isAdmin;


    // True indicates that this user is currently working, while false indicates the opposite.
    private boolean isWorking;

    // Describes the corporate title of an user.
    private String position;

    // Describes how long this account is valid;
    // TODO not sure about datatype. Has too many deprecated methods.
    private Date validity;

    public User(){}

    public User (String id, String name, String password, String token, boolean isAdmin,
                 boolean isWorking, String position, Date validity) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.token = token;
        this.isAdmin = isAdmin;
        this.isWorking = isWorking;
        this.position = position;
        this.validity = validity;
        this.isFirstLogin = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

