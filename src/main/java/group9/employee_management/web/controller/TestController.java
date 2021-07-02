package group9.employee_management.web.controller;

import group9.employee_management.application.service.WorkSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
public class TestController {
    @Autowired
    private WorkSessionService workSessionService;

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

    @GetMapping(
            value = "/whoami"
    )
    public String whoami(Principal principal) {
        return principal.getName();
    }

    @GetMapping(
            value = "/sessions"
    )
    public void ses(Principal principal) throws IOException {
        workSessionService.workSessionsToXML(principal.getName());
    }


    @GetMapping(
            value = "/time"
    )
    public void time() {
        System.out.println(WorkSessionService.getCurrentTime());
    }
}

