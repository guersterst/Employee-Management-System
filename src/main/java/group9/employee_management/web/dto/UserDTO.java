package group9.employee_management.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import group9.employee_management.persistence.entities.User;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Date;

/**
 * A container used for communication between the controllers and the client.
 */
public class UserDTO {

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("userName")
    private String userName;
    private String password;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    @JsonProperty("position")
    private String position;
    @JsonProperty("validity")
    private Date validity;

    public UserDTO(){}

    /**
     * Converts an User entity into an UserDTO.
     *
     * @param user The entity to be converted.
     * @return The DTO created from the entity
     */
    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUserName(user.getUserName());
        userDTO.setAdmin(user.isAdmin());
        userDTO.setPosition(user.getPosition());
        userDTO.setValidity(user.getValidity());
        return userDTO;
    }

    /**
     * Converts a user to JSON, excluding the password and work-sessions attributes.
     *
     * @return A JSON representation of the user.
     */

    public String toJSON() {
        return "{ \"firstName\": \"" + firstName + "\", " +
                "\"lastName\": \"" + lastName + "\" ," +
                "\"userName\": \"" + userName + "\", " +
                "\"isAdmin\": \"" + isAdmin + "\", " +
                "\"position\": \"" + position + "\", " +
                "\"validity\": \"" + validity + "\"}";
    }


    /*
    Getters and Setters
     */
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

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }
}
