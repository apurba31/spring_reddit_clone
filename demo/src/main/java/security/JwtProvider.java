package security;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import javax.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import exceptions.SpringRedditException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import lombok.Value;
import model.User;
import io.jsonwebtoken.Claims;

@Service
@Component
public class JwtProvider 
{
	public String generateToken(Authentication authentication)
	{
	    private KeyStore keyStore;
	    @Value("${jwt.expiration.time}") 
	    Long jwtExpirationInMillis;

	    @PostConstruct
	    public void init() {
	        try {
	            keyStore = KeyStore.getInstance("JKS");
	            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
	            keyStore.load(resourceAsStream, "secret".toCharArray());
	        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
	            throw new SpringRedditException("Exception occurred while loading keystore", e);
	        }

	    }
		
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUserName())
				.signWith(getPrivateKey())
				.compact();
	}
	
	private PrivateKey getPrivateKey()
	{
		try {
			return (PrivateKey) KeyStore.getKey("springblog", "secret".toCharArray());
		}
		catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e)
		{
			throw new SpringRedditException("Exception occured while retreiving public key from keystore", e);
		}
	}
	
	public void validationToken(String jwt)
	{
		parser().setSigningKey(getPublickey())
	}
	
    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRedditException("Exception occured while " +
                    "retrieving public key from keystore", e);
        }
    }
    
    public String getUsernameFromJwt(String token)
    {
    	Claims claims	= parser()
			    			.setSigningKey(getPublickey())
			    			.parseClaimsJws(token)
			    			.getBody();
    	
    	return claims.getSubject();
    }
    
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
