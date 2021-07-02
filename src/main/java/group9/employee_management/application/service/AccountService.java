package group9.employee_management.application.service;

import group9.employee_management.application.Roles;
import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.persistence.entities.Employee;
import group9.employee_management.persistence.entities.User;
import group9.employee_management.persistence.entities.WorkSession;
import group9.employee_management.persistence.repositories.EmployeeRepository;
import group9.employee_management.persistence.repositories.UserRepository;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A service to perform account manipulation or creation functions.
 */
@Service
public class AccountService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AccountService(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
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
        assert employeeRepository != null;
        return employeeRepository.userExistsByUserName(userName);
    }

    /*
    User creation
     */

    /**
     * Constructor for an user, automatically creates and associates with a new employee.
     */
    public Employee createUser(String userName, String firstName, String lastName, String password, boolean isAdmin,
                           String position) {
        Employee newEmployee = new Employee(userName, firstName, lastName, isAdmin, position);
        employeeRepository.save(newEmployee);
        User newUser = new User(userName, encoder.encode(password), newEmployee, Roles.USER);
        if(isAdmin) {
            newUser.setRoles(List.of(Roles.USER, Roles.ADMIN));
        }
        userRepository.save(newUser);
        return newEmployee;
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
        assert employeeRepository != null;
        Employee employee = employeeRepository.getUserByUserName(userName);

        if (employee == null) {
            throw new NoSuchUserException(userName);
        } else {
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employeeRepository.save(employee);
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
        assert employeeRepository != null;
        Employee employee = employeeRepository.getUserByUserName(userName);

        if (employee == null) {
            throw new NoSuchUserException(userName);
        } else {
            employee.setPosition(position);
            employeeRepository.save(employee);
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
        assert employeeRepository != null;
        Employee employee = employeeRepository.getUserByUserName(userName);
        User user = userRepository.getById(userName);

        if (employee == null) {
            throw new NoSuchUserException(userName);
        } else {
            employee.setAdmin(admin);
            employeeRepository.save(employee);
            if (admin) {
                user = new User(user.getUsername(), user.getPassword(), employee, List.of(Roles.USER, Roles.ADMIN));
            } else {
                user = new User(user.getUsername(), user.getPassword(), employee, List.of(Roles.USER));
            }
            userRepository.save(user);
        }
    }

    public UserDTO getUserAsDTO(String userName) throws NoSuchUserException {
        Employee employee = employeeRepository.getUserByUserName(userName);

        if (employee == null) {
            throw new NoSuchUserException(userName);
        } else {
            UserDTO userDTO = UserDTO.fromEntity(employee);
            userDTO.setPassword(userRepository.getById(userName).getPassword());
            return userDTO;
        }
    }

    /**
     * Returns a users admin rights.
     * @param userName The user identifier.
     * @return Admin rights of the user.
     * @throws NoSuchUserException When the given username is wrong.
     */
    public boolean isAdmin(String userName) throws NoSuchUserException {
        User user = userRepository.getById(userName);

        if (user == null) {
            throw new NoSuchUserException(userName);
        } else {
            List<Roles> list = user.getRoles();
            boolean isAdmin = false;

            for (Roles roles : list) {
                if (roles == Roles.ADMIN) {
                    isAdmin = true;
                    break;
                }
            }

            return isAdmin;
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
        assert employeeRepository != null;
        User user = userRepository.getById(userName);

        if (user == null) {
            throw new NoSuchUserException();
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
        assert employeeRepository != null;
        Employee employee = employeeRepository.getUserByUserName(userName);

        if (employee == null) {
            throw new NoSuchUserException(userName);
        } else {
            employee.setFirstLogin(isFirstLogin);
            employeeRepository.save(employee);
        }
    }

    /**
     * Delete a user as specified by the userName.
     * @param userName The username with which a user entity can be identified.
     * @throws NoSuchUserException Thrown if no user with the given name could be found.
     */
    public void deleteUser(String userName) throws NoSuchUserException {
        assert employeeRepository != null;
        Employee employee = employeeRepository.getUserByUserName(userName);

        if (employee == null) {
            throw new NoSuchUserException(userName);
        } else {
            userRepository.delete(userRepository.getById(userName));
            employeeRepository.delete(employee);
        }
    }
}
