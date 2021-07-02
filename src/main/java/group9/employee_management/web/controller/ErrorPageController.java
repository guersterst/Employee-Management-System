package group9.employee_management.web.controller;

import group9.employee_management.web.dto.StatusDTO;
import group9.employee_management.web.dto.UserDTO;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that's responsible for redirecting to our error page.
 */
@Controller
@RequestMapping("/error")
public class ErrorPageController implements ErrorController {
    /**
     * Mapping for a simple error page.
     * @return The errorPage.
     */
    @GetMapping(
            value = ""
    )
    public String get() {
        return "errorPage";
    }
}
