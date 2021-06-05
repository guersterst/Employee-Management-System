package group9.employee_management;

import group9.employee_management.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("")
    public String index() {
        return "index";
    }

    //TODO secure
    @RequestMapping(
            value = "/users/password/{id}",
    method = RequestMethod.GET)
    @ResponseBody
    public String getPassword(@PathVariable(value = "id") String id) {
        return userService.getPassword(id);
    }

    @RequestMapping(
            value = "/users/firstname/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public String getFirstName(@PathVariable(value = "id") String id) {
        return userService.getFirstName(id);
    }

    @RequestMapping(
            value = "/users/lastname/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public String getLastName(@PathVariable(value = "id") String id) {
        return userService.getLastName(id);
    }

    @RequestMapping(
            value = "/users/adminrights/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public boolean isAdmin(@PathVariable(value = "id") String id) {
        return userService.isAdmin(id);
    }

    @RequestMapping(
            value = "users/firstlogin/{id]",
            method = RequestMethod.GET)
    @ResponseBody
    public boolean isFirstLogin(@PathVariable(value = "id") String id) {
        return userService.isFirstLogin(id);
    }

    @RequestMapping(
            value = "users/token/{id]",
            method = RequestMethod.GET)
    @ResponseBody
    public String getToken(@PathVariable(value = "id") String id) {
        return userService.getToken(id);
    }


}
