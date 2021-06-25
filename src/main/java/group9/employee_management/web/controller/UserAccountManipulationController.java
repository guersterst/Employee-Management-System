package group9.employee_management.web.controller;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.service.AccountService;
import group9.employee_management.application.service.LoginService;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    //@ResponseBody
    public String get(Model model) {
        model.addAttribute("userCredentials", new UserDTO());
        model.addAttribute("status", new StatusDTO());
        return "userAccountPage";
    }


    // FOR an admin

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
    //@ResponseBody
    public String getUserDataAsAdmin(@ModelAttribute("userCredentials") UserDTO userCredentials,
                                     @ModelAttribute("status") StatusDTO status, @PathVariable(value =
            "userName") String userName) {
        if (accountService.userExistsByUserName(userName)) {
            userCredentials = accountService.getUserAsDTO(userName);
        } else {
            //throw new NoSuchUserException(userName);
            status.setMessage("user_not_found");
        }
        return "userAccountPage";
    }

    // FOR an user

    /**
     * Returns a users information in JSON format.
     * If there is no user with that user-name a {@code HttpStatus.NOT_FOUND} will be returned.
     *
     * @return
     */
    @GetMapping(
            value = "/me}"
    )
    //@ResponseBody
    public String getUserDataAsUser(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        if (accountService.userExistsByUserName(userName)) {
            userCredentials = accountService.getUserAsDTO(userName);
        } else {
            //throw new NoSuchUserException(userName);
            status.setMessage("user_not_found");
        }
        return "userAccountPage";
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
            value = "/{userName}/password"
    )
    //@ResponseBody
    public String setPasswordAsAdmin(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, @PathVariable("userName") String userName) {
        String password = userCredentials.getPassword();

        if (accountService.userExistsByUserName(userName)
                && password != null) {
            accountService.setPassword(userName, password);
            accountService.setIsFirstLogin(userName, false);
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "userAccountPage";
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
    //@ResponseBody
    public String setPasswordAsUser(@ModelAttribute("userCredentials") UserDTO userCredentials,
                                    @ModelAttribute("status") StatusDTO status, Principal principal) {

        String userName = principal.getName();
        String password = userCredentials.getPassword();

        if (accountService.userExistsByUserName(userName)
                && password != null) {
            accountService.setPassword(userName, password);
            accountService.setIsFirstLogin(userName, false);
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "userAccountPage";
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
            value = "/{userName}/name"
    )
    //@ResponseBody
    public String setNameAsAdmin(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, @PathVariable("userName") String userName) {
        String firstName = userCredentials.getFirstName();
        String lastName = userCredentials.getLastName();

        if (accountService.userExistsByUserName(userName)
                && firstName != null && lastName != null) {
            accountService.setName(userName, firstName, lastName);
            status.setMessage("valid");
            //return HttpStatus.OK;
        } else {
            status.setMessage("bad_request");
            //return HttpStatus.BAD_REQUEST;
        }
        return "userAccountPage";
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
    //@ResponseBody
    public String setNameAsUser(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status,Principal principal) {
        String userName = principal.getName();
        String firstName = userCredentials.getFirstName();
        String lastName = userCredentials.getLastName();

        if (accountService.userExistsByUserName(userName)
                && firstName != null && lastName != null) {
            accountService.setName(userName, firstName, lastName);
            status.setMessage("valid");
            //return HttpStatus.OK;
        } else {
            status.setMessage("bad_request");
            //return HttpStatus.BAD_REQUEST;
        }
        return "userAccountPage";
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
            value = "/{userName}/admin"
    )
    //@ResponseBody
    public String setAdminAsAdmin(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, @PathVariable("userName") String userName) {
        boolean admin = userCredentials.isAdmin();

        if (accountService.userExistsByUserName(userName)) {
            accountService.setAdmin(userName, admin);
            status.setMessage("valid");
            //return HttpStatus.OK;
        } else {
            status.setMessage("bad_request");
            //return HttpStatus.BAD_REQUEST;
        }
        return "userAccountPage";
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
            value = "/{userName}/position"
    )
    //@ResponseBody
    public String setPositionAsAdmin(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, @PathVariable("userName") String userName) {
        String position = userCredentials.getPosition();

        if (accountService.userExistsByUserName(userName) && position != null) {
            accountService.setPosition(userName, position);
            status.setMessage("valid");
            //return HttpStatus.OK;
        } else {
            status.setMessage("bad_request");
            //return HttpStatus.BAD_REQUEST;
        }
        return "userAccountPage";
    }
}
