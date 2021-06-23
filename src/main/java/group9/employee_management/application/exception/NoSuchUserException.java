package group9.employee_management.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no user with that user-name.")
public class NoSuchUserException extends RuntimeException {

    //TODO change from runtime exception
    public NoSuchUserException() {
        super("There is no user.");
    }

    public NoSuchUserException(String userName) {
        super("There is no user with the user name:" + userName);
    }
}
