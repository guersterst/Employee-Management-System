package group9.employee_management.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password is incorrect.")
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("This password is incorrect.");
    }
}
