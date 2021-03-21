package service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.AuthenticationResponse;
import dto.LoginRequest;
import dto.RegisterRequest;
import exceptions.SpringRedditException;
import model.NotificationMail;
import model.User;
import model.VerificationToken;
import repository.UserRepository;
import repository.VerificationTokenRepository;
import security.JwtProvider;

@Service
@Component
public class AuthService 
{
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MailService mailService;
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	private JwtProvider jwtProvider;
	
	@Transactional
	public void signup(RegisterRequest registerRequest)
	{
		User user = new User();
		user.setUserName(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        
        userRepository.save(user);
        
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationMail("Please activate your Account",
        		user.getEmail(), "Thank you for signing up to Spring Reddit, " +
        		"please click on the below url to activate your account :" +
        		"http://localhost:8080/api/auth/accountVerification/" + token));
	}
	
	private String generateVerificationToken(User user)
	{
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	
	public void verifyAccount(String token)
	{
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
		fetchUserAndEnable(verificationToken.get());
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		verificationToken.getUser().getUserName();
		User user = userRepository.findByUsername(user).orElseThrow(()-> new SpringRedditException("User not found with Name "+ username));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public AuthenticationResponse  login(LoginRequest loginRequest) 
	{
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		  return AuthenticationResponse.builder()
	                .authenticationToken(token)
	                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
	                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	                .username(loginRequest.getUsername())
	                .build();
	}
}
