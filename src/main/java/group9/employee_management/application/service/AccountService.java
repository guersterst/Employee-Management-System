package group9.employee_management.application.service;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.UserRepository;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collections;
import java.util.Set;

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

    /**
     * A function to determine whether a user with a certain name exists.
     *
     * @param userName The username with which a user entity can be identified.
     * @return {@code true} if the user exists and {@code false} otherwise.
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
        Set<WorkSession> emptySet = Collections.emptySet();
        userRepository.save(new User(userName, firstName, lastName,password, isAdmin, position, validityDate, emptySet));
    }

    /*
    User account manipulation
     */

    /**
     * A function to set the first and last name of an user entity.
     *
     * @param userName The username with which a user entity can be identified.
     * @param firstName The desired first name.
     * @param lastName The desired last name.
     * @throws NoSuchUserException Thrown if no user can be found with the given user name.
     */
    public void setName(String userName, String firstName, String lastName) throws NoSuchUserException {
        assert userRepository != null;
        User user = userRepository.getUserByUserName(userName);

        if (user == null) {
            throw new NoSuchUserException(userName);
        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userRepository.save(user);
        }
    }

    /**
     * A function to set an users job title.
     *
     * @param userName The username with which a user entity can be identified.
     * @param position The desired position title.
     * @throws NoSuchUserException Thrown if no user can be found with the given user name.
     */
    public void setPosition(String userName, String position) throws NoSuchUserException {
        assert userRepository != null;
        User user = userRepository.getUserByUserName(userName);

        if (user == null) {
            throw new NoSuchUserException(userName);
        } else {
            user.setPosition(position);
            userRepository.save(user);
        }
    }

    /**
     * A function to set an users admin rights.
     *
     * @param userName The username with which a user entity can be identified.
     * @param admin The desired admin rights.
     * @throws NoSuchUserException Thrown if no user can be found with the given user name.
     */
    public void setAdmin(String userName, boolean admin) throws NoSuchUserException {
        assert userRepository != null;
        User user = userRepository.getUserByUserName(userName);

        if (user == null) {
            throw new NoSuchUserException(userName);
        } else {
            user.setAdmin(admin);
            userRepository.save(user);
        }
    }

    /**
     * Returns an user as a JSON string.
     *
     * @param userName The users user-name.
     * @return The users information in JSON format, excluding his work-sessions and password.
     * @throws NoSuchUserException Thrown if no user can be found with the given user name.
     */
    public String getUserAsJSON(String userName) throws NoSuchUserException{
        User user = userRepository.getUserByUserName(userName);

        if (user == null) {
            throw new NoSuchUserException(userName);
        } else {
            return UserDTO.fromEntity(user).toJSON();
        }
    }

            /*
            First login setters.
            */

    /**
     * A function to set an users password.
     *
     * @param userName The username with which a user entity can be identified.
     * @param password The desired password.
     * @throws NoSuchUserException Thrown if no user can be found with the given user name.
     */
    public void setPassword(String userName, String password) throws NoSuchUserException {
        assert userRepository != null;
        User user = userRepository.getUserByUserName(userName);
        if (user == null) {
            throw new NoSuchUserException(userName);
        } else {
            user.setPassword(encoder.encode(password));
            userRepository.save(user);
        }
    }

    /**
     * A function to set an users first login value.
     *
     * @param userName The username with which a user entity can be identified.
     * @param isFirstLogin The desired value for if it is the first login.
     * @throws NoSuchUserException Thrown if no user can be found with the given user name.
     */
    public void setIsFirstLogin(String userName, boolean isFirstLogin) throws NoSuchUserException {
        assert userRepository != null;
        User user = userRepository.getUserByUserName(userName);

        if (user == null) {
            throw new NoSuchUserException(userName);
        } else {
            user.setFirstLogin(isFirstLogin);
            userRepository.save(user);
        }
    }
}
