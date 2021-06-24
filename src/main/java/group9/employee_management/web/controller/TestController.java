package group9.employee_management.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(
            value = "/user"
    )
    public String user() {
        return "<h1>User</h1>";
    }

    @GetMapping(
            value = "/admin"
    )
    public String admin() {
        return "<h1>Admin</h1>";
    }

    @GetMapping(
            value = "/all"
    )
    public String all() {
        return "<h1>All</h1>";
    }
}
