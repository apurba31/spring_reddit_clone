package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.AuthenticationResponse;
import dto.LoginRequest;
import dto.RegisterRequest;
import service.AuthService;

// DTO(Data Transfer Object)
@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
	private AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest)
	{
		authService.signup(registerRequest);
		 
		return new ResponseEntity<>("User Registration Succesful",
				HttpStatus.OK);
	}
	
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token)
	{
		authService.verifyAccount(token);
		return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)
	{
		return authService.login(loginRequest);
	}
}
