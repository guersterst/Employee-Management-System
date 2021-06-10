package group9.employee_management.web.controller;

import group9.employee_management.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public String index() {
        return "index";
    }

    /*
    //TODO secure
    @RequestMapping(
            value = "/users/password/{id}",
    method = RequestMethod.GET)
    @ResponseBody
    public String getPassword(@PathVariable(value = "id") String id) {
        return userService.getPassword(id);
    }
     */

    @PostMapping(
            value = "/users/authentication/{name}/{password}")
    @ResponseBody
    public HttpStatus authorize(@PathVariable(value = "name") String name,
                                          @PathVariable(value = "password") String password){
        if (userService.match(password, name)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping(
            value = "/users/name/{id}")
    public String getFirstName(@PathVariable(value = "id") String id) {
        return userService.getName(id);
    }

    @GetMapping(
            value = "/users/admin-rights/{id}")
    public boolean isAdmin(@PathVariable(value = "id") String id) {
        return userService.isAdmin(id);
    }

    @GetMapping(
            value = "users/first-login/{id}")
    public boolean isFirstLogin(@PathVariable(value = "id") String id) {
        return userService.isFirstLogin(id);
    }

    @GetMapping(
            value = "users/token/{id}")
    public String getToken(@PathVariable(value = "id") String id) {
        return userService.getToken(id);
    }


}
