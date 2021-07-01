package group9.employee_management.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "This user does not have any sessions.")
public class NoSessionsException extends RuntimeException {

    public NoSessionsException(){
        super();
    }

    public NoSessionsException(String userName) {
        super(userName + "Dose not have any sessions.");
    }
}
