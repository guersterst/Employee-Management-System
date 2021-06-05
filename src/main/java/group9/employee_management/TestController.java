package group9.employee_management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/adminCreateUser")
    public String index() {
        return "adminCreateUserAccount";
    }
}
