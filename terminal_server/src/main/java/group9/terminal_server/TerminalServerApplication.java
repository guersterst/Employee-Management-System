package group9.terminal_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TerminalServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TerminalServerApplication.class, args);
	}

	/**
	 * Terminalserver: userMainpage -> schickt anfragen an mainserver
	 * -> hat wsl koordinaten und login credentials
	 * -> auth am besten mit jwt
	 * -> kein restcontroller
	 * -> thymeleaf frontkomponente
	 * -> tomcat instanz
	 * -> kommunikations abfragen an den hauptserver
	 */

}
