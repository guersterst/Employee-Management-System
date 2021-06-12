package group9.employee_management.persistence.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * An Entity describing a user of the terminal.
 */
@Entity
public class User {

    @Id
    //@Column(name = "user_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2") //Best practice?
    //TODO replace
    private String id;

    // Replace id with userName -> Auto generate

    // Employee or admin name.
    private String name;

    //TODO userName? z.B. goeller01 -> auto generate siehe studip

    //TODO: password security.
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

    public User(){}

    public User (String name, String password, boolean isAdmin,
                 boolean isWorking, String position, Date validity) {
        super();
        this.name = name;
        this.password = password;
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

