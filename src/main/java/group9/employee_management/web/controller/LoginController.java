package group9.employee_management.web.controller;

import group9.employee_management.application.service.LoginService;
import group9.employee_management.web.dto.UserDTO;
import group9.employee_management.web.dto.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    //AUTH none

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    private final LoginService loginService;


    /**
     * Show the login page.
     *
     * @param model The model.
     * @return The login page ("index.html")
     */
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("userCredentials", new UserDTO());
        model.addAttribute("status", new StatusDTO());
        return "index";
    }

    /**
     * Determine whether a user is allowed to login or has to create his password.
     *
     * @param userCredentials A dto containing the users login information.
     * @return {@code HttpStatus.BAD_REQUEST} if the password is incorrect, {@code HttpStatus.NOT_FOUND} if the
     * there is no user with that name. {@code HttpStatus.TOO_EARLY} if its a first time login and the initial login
     * could be performed with the given login credentials. Else OK will be
     * returned.
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
                //return HttpStatus.TOO_EARLY;
            } else {

                // Indicate that this is a valid login.
                status.setMessage("valid");
                return "employeeView";
                //return HttpStatus.OK;
            }
        } else {

            // Indicate that this user does not exist.
            status.setMessage("not_found");
            return "index";
            //return HttpStatus.NOT_FOUND;
        }
    }
}
