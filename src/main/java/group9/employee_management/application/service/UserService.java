package group9.employee_management.application.service;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.exception.WrongPasswordException;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A service to interact with the user tables.
 */
@Service
public class UserService {

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);


    /**
     * Validation whether password and name form a correct pair.
     * If unsuccessful a descriptive exception will be thrown.
     *
     * @param password The given password.
     * @param name     The given name.
     */
    public void match(String password, String name) throws WrongPasswordException, NoSuchUserException {
        if (userRepository.userExistsByName(name)) {
            String encodedPW = userRepository.findPasswordById(userRepository.findIdByName(name));
            if (!encoder.matches(password, encodedPW)) {
                throw new WrongPasswordException();
            }
        } else {
            throw new NoSuchUserException(name);
        }
    }

    /*
    Login getters.
     */
    public String getName(String id) {
        assert userRepository != null;
        return userRepository.findNameById(id);
    }

    public boolean isAdmin(String id) {
        assert userRepository != null;
        return userRepository.findIsAdminById(id);
    }

    public boolean isFirstLogin(String id) {
        assert userRepository != null;
        return userRepository.findIsFirstLoginById(id);
    }

    public boolean isFirstLoginByName(String name) {
        assert userRepository != null;
        return userRepository.findIsFirstLoginById(userRepository.findIdByName(name));
    }

    /*
    First login setters.
     */
    public void setPassword(String id, String password) {
        assert userRepository != null;
        User user = userRepository.getById(id);
        user.setPassword(password);
        userRepository.save(user);
    }

    public void setIsFirstLogin(String id, boolean isFirstLogin) {
        assert userRepository != null;
        User user = userRepository.getById(id);
        user.setFirstLogin(isFirstLogin);
        userRepository.save(user);
    }
}
