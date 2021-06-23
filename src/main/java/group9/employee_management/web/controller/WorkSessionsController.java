package group9.employee_management.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/employees/worksessions")
public class WorkSessionsController {

    @GetMapping(
            value = ""
    )
    public String get() {

        //TODO
        return "admin-view";
    }

    //PUT MESSAGETOLATEST

    //POST STARTWORKSESSION
    //POST ENDWORKSESSION
    //PUT AVAILABILITY
    //GET AVAILABILITY
}
