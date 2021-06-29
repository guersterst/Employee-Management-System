package group9.employee_management.web.controller;

import group9.employee_management.application.service.AccountService;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/* This controller allows the admin to change a user's profile settings
 */
@Controller
@RequestMapping("/admin/account")
public class AdminUserAccountManipulationController {

    private final AccountService accountService;

    @Autowired
    public AdminUserAccountManipulationController(AccountService accountService) {
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
    public String get(Model model, Principal principal) {
        model.addAttribute("userCredentials", new UserDTO());
        model.addAttribute("status", new StatusDTO());
        return "userAccountPage";
    }

    @GetMapping(
            value = "/{userName}"
    )
    public String getUserDataAsUser2(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, Principal principal, Model model, @PathVariable("userName") String userName) {
        //String userName = //principal.getName();

        UserDTO userDTO = accountService.getUserAsDTO(userName);

        if (userDTO != null) {
            model.addAttribute("userCredentials", userDTO); // The user exists. Thus, the DTO we add to the model is userDTO
            status.setMessage("admin_valid"); // Inform frontend that admin is visiting the view and everything went fine
        } else { // This should generally not happen. A user, who is logged in, has a corresponding database entry.
            model.addAttribute("userCredentials", new UserDTO());  // Create new UserDTO as no user was found
            status.setMessage("not_found");
        }

        model.addAttribute("status", new StatusDTO());
        return "userAccountPage";
    }

    @PostMapping(
            value = "/edit/{userName}"
    )
    public String edit(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status,Principal principal, @PathVariable("userName") String userName) {

        // Get the name.
        //String userName = principal.getName();
        userCredentials = accountService.getUserAsDTO(userName);
        String firstName = userCredentials.getFirstName();
        String lastName = userCredentials.getLastName();

        // If the given user exists, we change their name.
        if (accountService.userExistsByUserName(userName)
                && firstName != null && lastName != null) {
            accountService.setName(userName, firstName, lastName);
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }

        // If the given user exists and their password is not null, we can change the password
        String password = userCredentials.getPassword();
        if (accountService.userExistsByUserName(userName)
                && password != null) {
            accountService.setPassword(userName, password);
            accountService.setIsFirstLogin(userName, false);
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }

        return "redirect:/admin/account/" + userName;
    }
}
