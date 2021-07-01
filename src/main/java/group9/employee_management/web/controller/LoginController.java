package group9.employee_management.web.controller;

import group9.employee_management.application.service.LoginService;
import group9.employee_management.web.dto.UserDTO;
import group9.employee_management.web.dto.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * NOT IN USE.
 */
@Controller
//@RequestMapping("")
//@RequestMapping("/login")
public class LoginController {

    //AUTH none

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    private final LoginService loginService;

    /**
     * The login-page should be displayed for the given mapping.
     * @return The login-page.
     */
    @GetMapping("/login")
    public String login() {
        return "index";
    }

    /**
     * The login-page should be displayed for the given mapping.
     * @return The login-page.
     */
    @GetMapping("")
    public String loginWithDifferentMapping() {
        return "index";
    }

    // UNNECESSARY:
    /**
     * Determine whether a user is allowed to login or has to create his password.
     *
     * @param userCredentials A dto containing the users login information.
     * @param status A DTO containing the status, e.g. whether the user logs in for the first time or was not found.
     *               "first_login" means that the user needs to set their password as they log in for the first time
     *               "valid" means that the operation was successful.
     *               "not_found means" that the user could not be found as either the username or the password were
     *               incorrect.
     * @return The view to display. We stay on "index" if the password has to be set or if the log in was not
     * successful. Otherwise, the employeeView is shown.
     */
    @PostMapping(
            value = "/authentication")
    public String login(@ModelAttribute("userCredentials") UserDTO userCredentials,
                            @ModelAttribute("status") StatusDTO status) {
        String userName = userCredentials.getUserName();
        String password = userCredentials.getPassword();

        if (loginService.isUser(userCredentials.getUserName()) && password != null && userName != null) {
            loginService.match(userCredentials.getPassword(), userCredentials.getUserName());
            if (loginService.isFirstLogin(userCredentials.getUserName())) {

                // Indicate that it is a first time login.
                status.setMessage("first_login");
                return "index";
            } else {
                //if (!...Service.isAdmin()) {
                // Indicate that this is a valid login.
                    status.setMessage("valid");
                    return "employeeView";
                //} else {
                // status.setMessage("valid");
                // return "adminView";
                //}
            }
        } else {

            // Indicate that this user does not exist.
            status.setMessage("not_found");
            return "index";
        }
    }
}
