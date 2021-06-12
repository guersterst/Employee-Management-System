package group9.employee_management.application.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String name) {
        super("There is no user with the name: " + name);
    }
}
