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

    private UserService userService;

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
     *
     * @param userCredentials A dto containing the users login information.
     * @return {@code HttpStatus.BAD_REQUEST} if the password is incorrect, {@code HttpStatus.NOT_FOUND} if the
     * there is no user with that name. Else OK will be returned.
     */
    @PostMapping(
            value = "/authentication")
    //+ "/{name}/{pw}")
    @ResponseBody
    public HttpStatus login(@ModelAttribute("loginForm") UserDTO userCredentials) {
        //@PathVariable("name") String name, @PathVariable("pw") String pw)
        //TODO if is first login
        //TODO check for necessary dto information
        try {
            userService.match(userCredentials.getPassword(), userCredentials.getName());
        } catch (NoSuchUserException noSuchUserException) {
            return HttpStatus.NOT_FOUND;
        } catch (WrongPasswordException ex) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    //?? GET Redirect after login attempt

    // POST password set

}
