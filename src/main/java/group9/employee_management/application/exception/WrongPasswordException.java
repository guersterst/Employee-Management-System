package group9.employee_management.application.exception;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("This password is incorrect.");
    }
}
