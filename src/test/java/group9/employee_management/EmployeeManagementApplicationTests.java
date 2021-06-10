package group9.employee_management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootTest
class EmployeeManagementApplicationTests {

	@Test
	void testPassWordMatching() throws IOException {

		URL url = new URL("http://localhost:8080/users/authentification/H.P.Baxxter/h0wmUchisthef1sh");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		System.out.println(con.getResponseCode());
		assert con.getResponseCode() == 200;
	}

}
