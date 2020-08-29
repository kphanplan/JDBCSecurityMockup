package com.techelevator.SecurityTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {
	
	//three apis, one for each level of authentication
	@GetMapping("/")
	public String home() {
		return ("<h1> HELLO WORLD </h1>");
	}
	
	@GetMapping("/user")
	public String user() {
		return ("<h1> WELCOME, USER <h1>");
	}
	
	@GetMapping("/admin")
	public String admin() {
		return ("<h1> WELCOME, ADMIN <h1>");
	}
}
