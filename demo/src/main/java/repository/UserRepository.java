package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import model.User;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
