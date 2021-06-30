package group9.employee_management.web.controller;

import group9.employee_management.application.service.AccountService;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 *  This controller allows the admin to change a user's profile settings.
 *  The admin needs different mappings as UserAccountManipulationController
 *  uses Principal to get a user's name. Thus, a user can edit their own profile only.
 *  The admin, however, can select any user and view/edit their profile.
 *  We use pathvariables to accomplish this.
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
        model.addAttribute("selectedUser", "");
        return "userAccountPage";
    }

    @GetMapping(
            value = "/{userName}"
    )
    public String getData(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, Principal principal, Model model, @PathVariable("userName") String userName) {
        //String userName = //principal.getName();

        UserDTO userDTO = accountService.getUserAsDTO(userName);
        model.addAttribute("selectedUser", userName); // Can be accessed in userAccountPage to determine the user to edit

        System.out.println(model.getAttribute("selectedUser"));

        if (userDTO != null) {
            model.addAttribute("userCredentials", userDTO); // The user exists. Thus, the DTO we add to the model is userDTO
            status.setMessage("admin_valid"); // Inform frontend that admin is visiting the view and everything went fine
        } else { // This should generally not happen. A user, who is logged in, has a corresponding database entry.
            model.addAttribute("userCredentials", new UserDTO());  // Create new UserDTO as no user was found
            status.setMessage("not_found");
        }

        model.addAttribute("status", status);
        return "userAccountPage";
    }

    @PostMapping(
            value = "/edit/{userName}"
    )
    public String edit(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status,Principal principal, @PathVariable("userName") String userName, Model model) {

        // Get the name.
        //String userName = principal.getName();
        userCredentials = accountService.getUserAsDTO(userName);
        String firstName = userCredentials.getFirstName();
        String lastName = userCredentials.getLastName();

        // If the given user exists, we change their name.
        if (accountService.userExistsByUserName(userName)
                && firstName != null && lastName != null) {
            accountService.setName(userName, firstName, lastName);
            status.setMessage("admin_valid");
        } else {
            status.setMessage("bad_request");
        }

        // If the given user exists and their password is not null, we can change the password
        String password = userCredentials.getPassword();
        if (accountService.userExistsByUserName(userName)
                && password != null) {
            accountService.setPassword(userName, password);
            accountService.setIsFirstLogin(userName, false);
            status.setMessage("admin_valid");
        } else {
            status.setMessage("bad_request");
        }

        model.addAttribute("status", status);
        return "redirect:/admin/account/" + userName;
    }

    /**
     * Admin should be able to delete users. Use this mapping to delete a user as specified by {userName}
     * @param userCredentials
     * @param status Not explicitly used.
     * @param userName The path variable which defines the user.
     * @return The view to display. The admin should be returned to /admin/employees to see all remaining users.
     */
    @GetMapping(
            value = "/delete/{userName}"
    )
    public String delete(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, @PathVariable("userName") String userName) {

        if (accountService.userExistsByUserName(userName)) {
            accountService.deleteUser(userName);
        }

        return "redirect:/admin/employees";
    }
}
