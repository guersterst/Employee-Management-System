package group9.employee_management.web.controller;

import group9.employee_management.application.service.LoginService;
import group9.employee_management.web.dto.UserDTO;
import group9.employee_management.web.dto.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Used to display the login-view and redirect a user after their login.
 */
@Controller
public class LoginController {

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

    /*
    /**
     * The login-page should be displayed for the given mapping.
     * @return The login-page.
     */
    @GetMapping("")
    public String loginWithDifferentMapping() {
        return "index";
    }


    /**
     * The user is redirected after a successful login. Here, we decide whether it's a user's first login and they
     * have to change their password or if they can be redirected to their main view. This is not necessarily a clean
     * implementation and gives the user the ability to use the navbar to skip setting their new password.
     *
     * @param principal Used to get who is logged in. Necessary to determine if it's a user's first login or not.
     * @return Decides where to redirect the user. "redirect:/first-login", if the user has logged in for the first
     * time, "redirect:/my-session/latest" otherwise.
     */
    @GetMapping(value = "/redirect")
    public String redirect(Principal principal) {
        if (loginService.isFirstLogin(principal.getName())) {
            return "redirect:/first-login";
        } else {
            return "redirect:/my-session/latest";
        }
    }
}
