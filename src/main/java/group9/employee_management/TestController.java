package group9.employee_management;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    @GetMapping("") //HTTP GET requests with /greeting in URL are mapped to this method
    //Parameter name is bound into name var of greeting. However, name is not 'required' --> defaultValue is used instead
    public String index() {
        return "index";
    }
}
