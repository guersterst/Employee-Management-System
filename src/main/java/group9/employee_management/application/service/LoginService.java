package group9.employee_management.application.service;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.exception.WrongPasswordException;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A service to perform login functions.
 */
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder(10);
    }


    /**
     * Validation whether password and name form a correct pair.
     * If unsuccessful a descriptive exception will be thrown.
     *
     * @param password The given password.
     * @param userName The given user name.
     */
    public void match(String password, String userName) throws WrongPasswordException, NoSuchUserException {
        if (userRepository.userExistsByUserName(userName)) {
            String encodedPW = userRepository.findPasswordByUserName(userName);
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
        return userRepository.userExistsByUserName(userName);
    }

    public boolean isFirstLogin(String userName) {
        assert userRepository != null;
        return userRepository.findIsFirstLoginByUserName(userName);
    }
}
