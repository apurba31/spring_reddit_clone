package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import model.Vote;

@Repository
@Component
public interface VoteRepository extends JpaRepository<Vote, Long>{

}
