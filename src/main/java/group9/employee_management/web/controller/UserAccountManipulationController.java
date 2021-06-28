package group9.employee_management.web.controller;

import group9.employee_management.application.service.AccountService;
import group9.employee_management.application.service.LoginService;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * This controller handles requests related to changing the users data.
 */
@Controller
@RequestMapping("/account")
public class UserAccountManipulationController {

    private final AccountService accountService;

    @Autowired
    public UserAccountManipulationController(AccountService accountService) {
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

    /**
     * Generates a new UserDTO, which contains the data saved to the corresponding user. The data stored in the
     * database about this user is subsequently written to the UserDTO and thus the frontend can access the userName,
     * firstName and so on.
     *
     * @param userCredentials The DTO carrying information about the user.
     * @param status Used to inform the frontend whether the operation was not successful ("user_not_found").
     * @param principal Description forthcoming.
     * @return the view to display
     */
    @GetMapping(
            value = "/me"
    )
    public String getUserDataAsUser(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status, Principal principal, Model model) {
        String userName = principal.getName();
        UserDTO userDTO = accountService.getUserAsDTO(userName);

        if (userDTO != null) {
            model.addAttribute("userCredentials", userDTO); // The user exists. Thus, the DTO we add to the model is userDTO
            status.setMessage("valid");
        } else { // This should generally not happen. A user, who is logged in, has a corresponding database entry.
            model.addAttribute("userCredentials", new UserDTO());  // Create new UserDTO as no user was found
            status.setMessage("not_found");
        }

        model.addAttribute("status", new StatusDTO());
        return "userAccountPage";
    }


    /**
     * Sets a new password and determines that this user must not set a new password the next
     * time he logs in.
     * If there is no user with that user-name a {@code HttpStatus.NOT_FOUND} will be returned, if the
     * model-attribute is insufficient a {@code HttpStatus.BAD_REQUEST} will be returned.
     *
     * @param userCredentials A dto containing the users username and new password.
     * @param status Used to inform the frontend about whether everything went fine ("valid") or if
     *               something went wrong ("bad_request").
     * @return Returns the view to display
     */
    @PutMapping(
            value = "/password"
    )
    //@ResponseBody
    public String setPasswordAsUser(@ModelAttribute("userCredentials") UserDTO userCredentials,
                                    @ModelAttribute("status") StatusDTO status, Principal principal) {

        String userName = principal.getName();
        String password = userCredentials.getPassword();

        if (accountService.userExistsByUserName(userName)
                && password != null) {
            accountService.setPassword(userName, password);
            accountService.setIsFirstLogin(userName, false);
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "userAccountPage";
    }

    /**
     * Sets a new first and last name for the user.
     * If there is no user with that user-name a {@code HttpStatus.NOT_FOUND} will be returned, if the
     * model-attribute is insufficient a {@code HttpStatus.BAD_REQUEST} will be returned.
     *
     * @param userCredentials A dto containing the users username and new first- and lastname.
     * @param status Used to inform the frontend about whether everything went fine ("valid") or if
     *               something went wrong ("bad_request").
     * @return Returns the view to display
     */
    @PutMapping(
            value = "/name"
    )
    //@ResponseBody
    public String setNameAsUser(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status,Principal principal) {
        String userName = principal.getName();
        String firstName = userCredentials.getFirstName();
        String lastName = userCredentials.getLastName();

        if (accountService.userExistsByUserName(userName)
                && firstName != null && lastName != null) {
            accountService.setName(userName, firstName, lastName);
            status.setMessage("valid");
        } else {
            status.setMessage("bad_request");
        }
        return "userAccountPage";
    }


    /**
     * Called via Thymeleaf by the frontend whenever the user submits changes to their user profile. Generally speaking,
     * the userAccountPage is first filled with the information currently stored in the database about the user (/me).
     * If the user then decides to make changes, edits e.g. the userName and then submits the form, the entire form
     * (including firstName, lastName and so on) are transferred. This method maps to the thymeleaf action used to
     * submit the form.
     * @param userCredentials The DTO used to store information about the user.
     * @param status Informs the frontend about whether the operation was successful ("valid") or not ("bad_request")
     * @param principal Description forthcoming.
     * @return
     */
    @PostMapping(
            value = "/edit"
    )
    public String edit(@ModelAttribute("userCredentials") UserDTO userCredentials, @ModelAttribute(
            "status") StatusDTO status,Principal principal) {

        // Get the name.
        String userName = principal.getName();
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

        return "redirect:/account/me";
    }
}
