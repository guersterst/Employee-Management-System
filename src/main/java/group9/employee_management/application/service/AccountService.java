package group9.employee_management.application.service;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * A service to perform account manipulation or creation functions.
 */
@Service
public class AccountService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    // Default validity date of an account.
    //TODO in relation to current date. f.e. +2 years
    Date validityDate = new Date(1640991600000L);

    @Autowired
    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder(10);
    }

    /*
    Getters for defensive programming/ validation.
     */

    public boolean userExistsByUserName(String userName) {
        assert userRepository != null;
        return userRepository.userExistsByUserName(userName);
    }

    /*
    User creation
     */

    /**
     * Constructor with default values set. Be aware that the password should now be a token for use in first time
     * login only.
     */
    public void createUser(String userName, String firstName, String lastName, String password, boolean isAdmin,
                           String position) {
        userRepository.save(new User(userName, firstName, lastName,password,isAdmin, false, position, validityDate));
    }

    /*
    User account manipulation
     */

    public void setName(String id, String firstName, String lastName) throws NoSuchUserException {
        assert userRepository != null;
        User user = userRepository.getById(id);

        if (user == null) {
            throw new NoSuchUserException(id);
        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userRepository.save(user);
        }
    }

            /*
            First login setters.
            */

    public void setPassword(String id, String password) throws NoSuchUserException {
        assert userRepository != null;
        User user = userRepository.getById(id);
        if (user == null) {
            throw new NoSuchUserException(id);
        } else {
            user.setPassword(encoder.encode(password));
            userRepository.save(user);
        }
    }

    public void setIsFirstLogin(String id, boolean isFirstLogin) throws NoSuchUserException {
        assert userRepository != null;
        User user = userRepository.getById(id);

        if (user == null) {
            throw new NoSuchUserException(id);
        } else {
            user.setFirstLogin(isFirstLogin);
            userRepository.save(user);
        }
    }
}
