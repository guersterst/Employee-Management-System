package group9.employee_management.web.controller;

import group9.employee_management.application.service.AccountService;
import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Controller used to set the password on a user's first login.
 */
@Controller
@RequestMapping("/first-login")
public class LoginForFirstTimeController {
    private final AccountService accountService;

    @Autowired
    public LoginForFirstTimeController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Display loginForFirstTime. Alternatively: use index.html and use the statusDTO to tell the view
     * to show the input fields for setting up a password + hide the input fields used for the login.
     * @param model The model we use to add the DTOs as attributes.
     * @return The view to display.
     */
    @GetMapping(
            value = "")
    public String firstLogin(Model model) {
        model.addAttribute("userCredentials", new UserDTO());
        model.addAttribute("userCredentials", new StatusDTO());
        return "loginForFirstTime";
    }

    /**
     * Used to set the user's new password.
     * @param userCredentials The DTO, which contains the new password.
     * @param status The DTO used to communicate with the frontend about whether the operation was successful.
     * @param principal Description forthcoming.
     * @return The view to display. Redirect to user's session if setting up the password was successful.
     */
    @PostMapping(
            value = "/set-password")
    public String setPassword(@ModelAttribute("userCredentials") UserDTO userCredentials,
                              @ModelAttribute("status") StatusDTO status, Principal principal) {
        String userName = principal.getName();

        if (userName != null && userCredentials.getPassword() != null) {
            accountService.setIsFirstLogin(principal.getName(), false);
            accountService.setPassword(userName, userCredentials.getPassword());
            return "redirect:/my-session/latest";
        } else {
            status.setMessage("bad_request");
            return "loginForFirstTime";
        }
    }
}
