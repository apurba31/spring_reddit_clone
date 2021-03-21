package service;

import java.util.Collection;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import model.User;
import repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService
{
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public UserDetails loadsUserByUsername(String username)
	{
		Optional<User> userOptional = userRepository.findByUsername(username);
		User user = userOptional
				.orElseThrow(() -> new UsernameNotFoundException("No user" +
											"Found with Username" + username));
		return org.springframework.security
				.core.userdetails.User(user.getUsername(), user.getPassword(),
						user.isEnabled(), true, true,
						true, getAuthorities("USER"));
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(String role)
	{
		return singletonList(new SimpleGrantedAuthority(role));
	}
}
