package group9.employee_management.web.dto;

/**
 * Used to deliver messages to the frontend, which can then be interpreted.
 * For instance, if a user logs in for the first time, we can set a specific
 * message, which in turn lets the frontend know to display input fields for
 * setting a new password.
 */
public class StatusDTO {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}