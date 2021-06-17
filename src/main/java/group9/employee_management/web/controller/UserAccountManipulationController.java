package group9.employee_management.web.controller;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.AccountService;
import group9.employee_management.application.service.LoginService;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/accounts")
public class UserAccountManipulationController {

    //TODO PUT OR POST

    //Auth admin -> not for setPassword
    private final AccountService accountService;

    @Autowired
    public UserAccountManipulationController(AccountService accountService, LoginService loginService) {
        this.accountService = accountService;
    }

    /**
     * Sets a new password and determines that this user must not set a new password the next
     * time he logs in.
     *
     * @param userCredentials A dto containing the users id and new password.
     * @return Returns {@code HttpStatus.OK} if the operation was successful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise.
     */
    @PutMapping(
            value = "/password"
    )
    @ResponseBody
    public HttpStatus setPassword(@ModelAttribute("loginForm") UserDTO userCredentials) {

        String userName = userCredentials.getUserName();
        String password = userCredentials.getPassword();

        if (accountService.userExistsByUserName(userName)
                && password != null && userName != null) {
            try {
                accountService.setPassword(userName, password);
                accountService.setIsFirstLogin(userName, false);
            } catch (NoSuchUserException exception) {
                return HttpStatus.BAD_REQUEST;
            }
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }


    /**
     * Sets a new first and last name for the user.
     *
     * @param userCredentials A dto containing the users id and new name.
     * @return Returns {@code HttpStatus.OK} if the operation was successful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise.
     */
    @PutMapping(
            value = "/name"
    )
    @ResponseBody
    public HttpStatus setName(@ModelAttribute("accountForm") UserDTO userCredentials) {

        String userName = userCredentials.getUserName();
        String firstName = userCredentials.getFirstName();
        String lastName = userCredentials.getLastName();

        if (accountService.userExistsByUserName(userName)
                && firstName != null && lastName != null && userName != null) {
            try {
                accountService.setName(userName, firstName, lastName);
            } catch (NoSuchUserException exception) {
                return HttpStatus.BAD_REQUEST;
            }
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Sets the admin rights for a user.
     *
     * @param userCredentials A dto containing the users id and new name.
     * @return Returns {@code HttpStatus.OK} if the operation was successful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise.
     */
    @PutMapping(
            value = "/admin"
    )
    @ResponseBody
    public HttpStatus setAdmin(@ModelAttribute("accountForm") UserDTO userCredentials) {

        String userName = userCredentials.getUserName();
        boolean admin = userCredentials.isAdmin();

        if (accountService.userExistsByUserName(userName) && userName != null) {
            try {
                accountService.setAdmin(userName, admin);
            } catch (NoSuchUserException exception) {
                return HttpStatus.BAD_REQUEST;
            }
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Sets the admin rights for a user.
     *
     * @param userCredentials A dto containing the users id and new name.
     * @return Returns {@code HttpStatus.OK} if the operation was successful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise.
     */
    @PutMapping(
            value = "/position"
    )
    @ResponseBody
    public HttpStatus setPosition(@ModelAttribute("accountForm") UserDTO userCredentials) {

        String userName = userCredentials.getUserName();
        String position = userCredentials.getPosition();

        if (accountService.userExistsByUserName(userName) && position != null) {
            try {
                accountService.setPosition(userName, position);
            } catch (NoSuchUserException exception) {
                return HttpStatus.BAD_REQUEST;
            }
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
