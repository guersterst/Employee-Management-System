package group9.employee_management.web.controller;

import group9.employee_management.application.service.AccountService;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users/accounts")
public class UserCreationController {

    //AUTH admin

    private final AccountService accountService;

    @Autowired
    public UserCreationController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Create a new user in the database
     *
     * @param newUser A dto containing the users account information.
     * @return {@code HttpStaus.Ok} if successful. {@code HttpStatus.BAD_REQUEST} if the information contained within
     * the dto is insufficient.
     */
    @PostMapping(
            value = "/creation")
    @ResponseBody
    public HttpStatus createUser(@ModelAttribute("createForm") UserDTO newUser) {
        if (newUser.getUserName() != null
                && newUser.getFirstName() != null
                && newUser.getLastName() != null
                && newUser.getPassword() != null
                //&& newUser.isAdmin() != null
                && newUser.getPosition() != null) {
            accountService.createUser(newUser.getUserName(), newUser.getFirstName(), newUser.getLastName(),
                    newUser.getPassword(), newUser.isAdmin(), newUser.getPosition());
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

}
