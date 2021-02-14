package controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.RegisterRequest;

// DTO(Data Transfer Object)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@PostMapping("/signup")
	public signup(@RequestBody RegisterRequest registerRequest)
	{
		return new ResponseEntity<>("User Registration Succesful", OK);
	}
}
