package group9.employee_management.web.controller;

import group9.employee_management.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(
            value = "name/{id:[1-9]+[0-9]*}")
    public String getFirstName(@PathVariable(value = "id") String id) {
        return userService.getName(id);
    }

    @GetMapping(
            value = "admin-rights/{id:[1-9]+[0-9]*}")
    public boolean isAdmin(@PathVariable(value = "id") String id) {
        return userService.isAdmin(id);
    }

    @GetMapping(
            value = "first-login/{id:[1-9]+[0-9]*}")
    public boolean isFirstLogin(@PathVariable(value = "id") String id) {
        return userService.isFirstLogin(id);
    }

    @GetMapping(
            value = "token/{id:[1-9]+[0-9]*}")
    public String getToken(@PathVariable(value = "id") String id) {
        return userService.getToken(id);
    }
}
