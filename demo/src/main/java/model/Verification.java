package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "token")
public class Verification 
{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private String token;
	@OneToOne(fetch = LAZY)
	private User user;
	private Instant expireDate;
}
