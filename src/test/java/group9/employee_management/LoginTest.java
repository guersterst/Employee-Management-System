package group9.employee_management;

import group9.employee_management.application.service.UserService;
import group9.employee_management.persistence.repositories.UserRepository;
import group9.employee_management.web.controller.LoginController;
import group9.employee_management.web.dto.UserDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
@WebMvcTest(value = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)

 */
@SpringBootTest
class LoginTest {


	//Aprivate MockMvc mockMvc = new MockMvc();


	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Test
	void testPassWordMatching() throws Exception {
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

		mockMvc.perform(get("http://localhost:8080/login/authentication/Farin Urlaub/abc123def"))
				.andDo(print())
				.andExpect(status().isOk());
	}




}
