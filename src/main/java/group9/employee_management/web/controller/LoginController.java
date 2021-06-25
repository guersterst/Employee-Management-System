package group9.employee_management.web.controller;

import group9.employee_management.application.service.LoginService;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
//@RequestMapping("/login")
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
    @GetMapping(
            value = "")
    public String index(Model model) {
        model.addAttribute("userCredentials", new UserDTO());

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userCredentials", new UserDTO());
        return "index";
    }

    /**
     * Determine whether a user is allowed to login or has to create his password.
     *
     * @param userCredentials A dto containing the users login information.
     *                        Requires an username and a password.
     * @return {@code HttpStatus.BAD_REQUEST} if the password is incorrect, {@code HttpStatus.NOT_FOUND} if the
     * there is no user with that name. {@code HttpStatus.TOO_EARLY} if its a first time login and the initial login
     * could be performed with the given login credentials. Else OK will be returned.
     */
    @GetMapping(
            value = "/authentication")
    @ResponseBody
    public HttpStatus login(@ModelAttribute("userCredentials") UserDTO userCredentials) {
        String userName = userCredentials.getUserName();
        String password = userCredentials.getPassword();

        if (loginService.isUser(userCredentials.getUserName()) && password != null && userName != null) {
            loginService.match(userCredentials.getPassword(), userCredentials.getUserName());
            if (loginService.isFirstLogin(userCredentials.getUserName())) {

                // Indicate that it is a first time login.
                return HttpStatus.TOO_EARLY;
            } else {

                // Indicate that this is a valid login.
                return HttpStatus.OK;
            }
        } else {

            // Indicate that this user does not exist.
            return HttpStatus.NOT_FOUND;
        }
    }
}
