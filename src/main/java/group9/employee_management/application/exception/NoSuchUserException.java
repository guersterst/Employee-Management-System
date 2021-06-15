package group9.employee_management.application.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
        super("There is no user.");
    }

    public NoSuchUserException(String userName) {
        super("There is no user with the user name:" + userName);
    }
}
