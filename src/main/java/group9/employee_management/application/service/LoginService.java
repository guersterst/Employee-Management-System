package group9.employee_management.application.service;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.exception.WrongPasswordException;
import group9.employee_management.persistence.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A service to perform login functions. NOT IN USE.
 */
@Service
public class LoginService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public LoginService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.encoder = new BCryptPasswordEncoder(10);
    }


    /**
     * NOT USABLE
     *
     *  Validation whether password and name form a correct pair.
     * If unsuccessful a descriptive exception will be thrown.
     *
     * @param password The given password.
     * @param userName The given user name.
     */
    @Deprecated
    public void match(String password, String userName) throws WrongPasswordException, NoSuchUserException {
        if (employeeRepository.userExistsByUserName(userName)) {
            String encodedPW = ""; //employeeRepository.findPasswordByUserName(userName);
            if (!encoder.matches(password, encodedPW)) {
                throw new WrongPasswordException();
            }
        } else {
            throw new NoSuchUserException(userName);
        }
    }

    /*
    Login getters.
     */

    public boolean isUser(String userName) {
        return employeeRepository.userExistsByUserName(userName);
    }

    public boolean isFirstLogin(String userName) {
        assert employeeRepository != null;
        return employeeRepository.findIsFirstLoginByUserName(userName);
    }
}
