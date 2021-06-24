package group9.employee_management.web.controller;

import group9.employee_management.application.service.AccountService;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/employees/accounts")
public class UserCreationController {

    //AUTH admin

    private final AccountService accountService;

    @Autowired
    public UserCreationController(AccountService accountService) {
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
        model.addAttribute("newUser", new UserDTO());

        return "adminCreateUserAccount";
    }

    /**
     * Create a new user in the database
     *
     * @param newUser A dto containing the users account information. (username, first- and lastname, password,
     *                adminrights and position.
     * @return {@code HttpStatus.Ok} if successful. {@code HttpStatus.BAD_REQUEST} if the information contained within
     * the dto is insufficient.
     */
    @PostMapping(
            value = "/creation")
    @ResponseBody
    public HttpStatus createUser(@ModelAttribute("newUser") UserDTO newUser) {
        if (newUser.getUserName() != null
                && newUser.getFirstName() != null
                && newUser.getLastName() != null
                && newUser.getPassword() != null
                && newUser.getPosition() != null) {
            accountService.createUser(newUser.getUserName(), newUser.getFirstName(), newUser.getLastName(),
                    newUser.getPassword(), newUser.isAdmin(), newUser.getPosition());
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

}
