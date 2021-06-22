package group9.employee_management.web.dto;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * A container used for communication between the controllers and the client.
 */
public class UserDTO {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isAdmin;
    private String position;

    //TODO availability
    //TODO onsite

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
