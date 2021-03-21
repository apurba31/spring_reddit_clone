package repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import model.VerificationToken;

@Component
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> 
{
	Optional<VerificationToken> findByToken(String token);
}
