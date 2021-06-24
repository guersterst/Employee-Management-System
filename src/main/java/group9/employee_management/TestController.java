package group9.employee_management;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This is a controller that allows quick testing for whether the login works (without validation).
 * The purpose is to check whether the index.html (responsible for login) can make POST-Requests.
 */
@Controller
public class TestController {

    /**
     * Show the login page.
     *
     * @param model The model.
     * @return The login page ("index.html")
     */
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("loginForm", new loginFormSubmission());
        return "index";
    }

    /**
     * Receives the POST-Request and acts accordingly. This is just a quick test. If the password
     * is "1234", the login is "successful". Otherwise an error is shown.
     *
     * @param submission The submission that is received from the post request.
     * @return The new template to navigate to.
     */
    @PostMapping("")
    public String submissionResult(@ModelAttribute("loginForm") loginFormSubmission submission) {
        if (submission.getPassword().equals("1234")) {
            return "employeeView";
        } else {
            return "redirect:?error";
        }
    }
}
