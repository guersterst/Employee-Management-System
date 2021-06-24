package group9.employee_management;

import group9.employee_management.application.service.LoginService;
import group9.employee_management.persistence.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/*
@WebMvcTest(value = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)

 */
@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    LoginService loginService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void testPassWordMatching() throws Exception {
/*
        UserDTO validUser = new UserDTO();
        validUser.setFirstName("Farin");
        validUser.setLastName("Urlaub");
        validUser.setPassword("abc123def");

 */


/*
        UserDTO invalidUserPW = new UserDTO();
        invalidUserPW.setName("Farin", "Urlaub");
        invalidUserPW.setPassword("abc13def");

        UserDTO invalidUserName = new UserDTO();
        invalidUserName.setName("Farin", "Arlaub");
        invalidUserName.setPassword("abc123def");

        UserDTO validUserFirstLogin = new UserDTO();
        validUserFirstLogin.setName("Kristoffer Jonas", "Klauß");
        validUserFirstLogin.setPassword("überallAnJederWand");

         */
/*

        //TODO am i using modelattribute wrongly?
        mockMvc.perform(post("/login/authentication")
                .flashAttr("loginForm", validUser))

                .andDo(print()).andExpect(status().isOk());

 */


/*
		URL url = new URL("http://localhost:8080/login/authentication/FarinUrlaub/abc123def");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		UserDTO userDTO = new UserDTO();
		userDTO.setName("Farin Urlaub");
		userDTO.setPassword("abc123def");

		con.setRequestMethod("GET");

		System.out.println(con.getResponseCode());
		assert con.getResponseCode() == 200;

 */
        /*
		mockMvc.perform(post("http://localhost:8080/login/authentication/Farin Urlaub/abc123def"))
				.andDo(print())
			.andExpect(status().isOk());

         */

    }
}
