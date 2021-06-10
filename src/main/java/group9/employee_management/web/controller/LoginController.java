package group9.employee_management.web.controller;

import group9.employee_management.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login/")
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
            value = "/{name}/{password}")
    @ResponseBody
    public HttpStatus login(@PathVariable(value = "name") String name,
                                          @PathVariable(value = "password") String password){
        if (userService.match(password, name)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
