package group9.employee_management;

import group9.employee_management.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    //TODO secure
    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping(
            value = "/users/password/{id}",
    method = RequestMethod.GET)
    @ResponseBody
    public String getPassword(@PathVariable(value = "id") String id) {
        return userService.getPassword(id);
    }

    @RequestMapping(
            value = "/users/name/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public String getName(@PathVariable(value = "id") String id) {
        return userService.getName(id);
    }

    @RequestMapping(
            value = "/users/adminrights/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public boolean isAdmin(@PathVariable(value = "id") String id) {
        return userService.isAdmin(id);
    }





}
