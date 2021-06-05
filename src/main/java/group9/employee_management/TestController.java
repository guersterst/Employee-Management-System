package group9.employee_management;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    @GetMapping("")
    public String index(Model model) {
        System.out.println("Hallo");
        model.addAttribute("loginForm", new loginFormSubmission());
        return "index";
    }

    @PostMapping("")
    public String submissionResult(@ModelAttribute("loginForm") loginFormSubmission submission) {
        System.out.println("Hallo");
        return "employeeView";
    }
}
