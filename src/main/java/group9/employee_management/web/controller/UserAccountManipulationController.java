package group9.employee_management.web.controller;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.AccountService;
import group9.employee_management.application.service.LoginService;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users/accounts")
public class UserAccountManipulationController {

    //AUTH own account

    private final AccountService accountService;

    @Autowired
    public UserAccountManipulationController(AccountService accountService, LoginService loginService) {
        this.accountService = accountService;
    }

    /**
     * Get access to the page and get a model-attribute of an user-dto.
     *
     * @param model The model.
     * @return The user creation page.
     */
    @GetMapping(
            value = ""
    )
    @ResponseBody
    public String get(Model model) {
        model.addAttribute("userCredentials", new UserDTO());

        return "userAccountPage";
    }

    /**
     * Returns a users information in JSON format.
     * If there is no user with that user-name a {@code HttpStatus.NOT_FOUND} will be returned.
     *
     * @param userName
     * @return
     */
    @GetMapping(
            value = "/{userName}"
    )
    @ResponseBody
    public String getUserData(@PathVariable(value = "userName") String userName) {
        if (accountService.userExistsByUserName(userName)) {
            return accountService.getUserAsJSON(userName);
        } else {
            throw new NoSuchUserException(userName);
        }
    }

    /**
     * Sets a new password and determines that this user must not set a new password the next
     * time he logs in.
     * If there is no user with that user-name a {@code HttpStatus.NOT_FOUND} will be returned, if the
     * model-attribute is insufficient a {@code HttpStatus.BAD_REQUEST} will be returned.
     *
     * @param userCredentials A dto containing the users username and new password.
     * @return Returns {@code HttpStatus.OK} if the operation was successful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise or {@code HttpStatus.NOT_FOUND}.
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
            accountService.setPassword(userName, password);
            accountService.setIsFirstLogin(userName, false);
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }


    /**
     * Sets a new first and last name for the user.
     * If there is no user with that user-name a {@code HttpStatus.NOT_FOUND} will be returned, if the
     * model-attribute is insufficient a {@code HttpStatus.BAD_REQUEST} will be returned.
     *
     * @param userCredentials A dto containing the users username and new first- and lastname.
     * @return Returns {@code HttpStatus.OK} if the operation was successful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise or {@code HttpStatus.NOT_FOUND
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
            accountService.setName(userName, firstName, lastName);
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Sets the admin rights for a user.
     * If there is no user with that user-name a {@code HttpStatus.NOT_FOUND} will be returned, if the
     * model-attribute is insufficient a {@code HttpStatus.BAD_REQUEST} will be returned.
     *
     * @param userCredentials A dto containing the users username and new admin rights.
     * @return Returns {@code HttpStatus.OK} if the operation was successful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise or {@code HttpStatus.NOT_FOUND
     */
    @PutMapping(
            value = "/admin"
    )
    @ResponseBody
    public HttpStatus setAdmin(@ModelAttribute("accountForm") UserDTO userCredentials) {

        String userName = userCredentials.getUserName();
        boolean admin = userCredentials.isAdmin();

        if (accountService.userExistsByUserName(userName) && userName != null) {
            accountService.setAdmin(userName, admin);
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Sets the admin rights for a user.
     * If there is no user with that user-name a {@code HttpStatus.NOT_FOUND} will be returned, if the
     * model-attribute is insufficient a {@code HttpStatus.BAD_REQUEST} will be returned.
     *
     * @param userCredentials A dto containing the users username and new position title.
     * @return Returns {@code HttpStatus.OK} if the operation was successful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise or {@code HttpStatus.NOT_FOUND
     */
    @PutMapping(
            value = "/position"
    )
    @ResponseBody
    public HttpStatus setPosition(@ModelAttribute("accountForm") UserDTO userCredentials) {

        String userName = userCredentials.getUserName();
        String position = userCredentials.getPosition();

        if (accountService.userExistsByUserName(userName) && position != null) {
            accountService.setPosition(userName, position);
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
