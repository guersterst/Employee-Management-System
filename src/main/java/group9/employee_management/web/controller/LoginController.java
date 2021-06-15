package group9.employee_management.web.controller;

import group9.employee_management.application.exception.NoSuchUserException;
import group9.employee_management.application.exception.WrongPasswordException;
import group9.employee_management.application.service.UserService;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    //AUTH none

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    /**
     * Show the login page.
     *
     * @param model The model.
     * @return The login page ("index.html")
     */
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("loginForm", new UserDTO());
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
    //+ "/{name}/{pw}")
    @ResponseBody
    public HttpStatus login(@ModelAttribute("loginForm") UserDTO userCredentials) {
        //@PathVariable("name") String name, @PathVariable("pw") String pw)
        //TODO if is first login
        //TODO check for necessary dto information

        if (userService.isFirstLoginByName(userCredentials.getName())) {
            try {
                userService.match(userCredentials.getPassword(), userCredentials.getName());
            } catch (NoSuchUserException noSuchUserException) {

                // Indicate that no user with that name exists.
                return HttpStatus.NOT_FOUND;
            } catch (WrongPasswordException ex) {

                // Indicate that password and name do not match.
                return HttpStatus.BAD_REQUEST;
            }

            // Indicate that it is a first time login.
            return HttpStatus.TOO_EARLY;
        } else {
            try {
                userService.match(userCredentials.getPassword(), userCredentials.getName());
            } catch (NoSuchUserException noSuchUserException) {

                // Indicate that no user with that name exists.
                return HttpStatus.NOT_FOUND;
            } catch (WrongPasswordException ex) {

                // Indicate that password and name do not match.
                return HttpStatus.BAD_REQUEST;
            }
            return HttpStatus.OK;
        }
    }

    /**
     * Sets the new password and determines that this user must not set a new password the next
     * time he logs in.
     * @param userCredentials A dto containing the users login information including the desired
     *                        password.
     * @return Returns {@code HttpStatus.OK} if the operation was succesful. Returns {@code HttpStatus.BAD_REQUEST}
     * otherwise.
     */
    @PutMapping(
            value = "/password-creation"
    )
    @ResponseBody
    public HttpStatus setPassword(@ModelAttribute("loginForm") UserDTO userCredentials) {

        String id = userCredentials.getId();
        String password = userCredentials.getPassword();

        if (userService.userExistsById(id)) {
            userService.setPassword(id, password);
            userService.setIsFirstLogin(id, false);
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    //?? GET Redirect after login attempt
}
