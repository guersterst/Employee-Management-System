package group9.employee_management.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import group9.employee_management.persistence.entities.Employee;

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
    private boolean admin;
    @JsonProperty("position")
    private String position;
    private int coordX;
    private int coordY;

    public UserDTO(){}

    /**
     * Converts an User entity into an UserDTO.
     *
     * @param employee The entity to be converted.
     * @return The DTO created from the entity
     */
    public static UserDTO fromEntity(Employee employee) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(employee.getFirstName());
        userDTO.setLastName(employee.getLastName());
        userDTO.setUserName(employee.getUserName());
        userDTO.setAdmin(employee.isAdmin());
        userDTO.setPosition(employee.getPosition());
        userDTO.setCoordX(employee.getCoordX());
        userDTO.setCoordY(employee.getCoordY());
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
                "\"isAdmin\": \"" + admin + "\", " +
                "\"position\": \"" + position + "\"}";
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
}
