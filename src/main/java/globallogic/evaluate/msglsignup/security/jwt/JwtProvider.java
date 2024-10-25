package globallogic.evaluate.msglsignup.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

/**
 * JWT provider responsible for creating and validating JWT tokens for authentication and authorization.
 *
 * <p>This class uses the <i>jjwt</i> library to generate and validate tokens with HS256 signing.
 * It also handles secret key encoding and manages exceptions when tokens are invalid or expired.</p>
 */
@Log4j2
@Component
public class JwtProvider {

    private final String secretKey;
    private final long validityInMilliseconds;

    /**
     * Constructor that initializes the secret key and token validity period.
     *
     * @param secretKey              The key used to sign the tokens.
     * @param validityInMilliseconds The token validity period in milliseconds.
     */
    @Autowired
    public JwtProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                       @Value("${security.jwt.token.expiration}") long validityInMilliseconds) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    /**
     * Creates a JWT access token using the provided email as the subject.
     *
     * <p>The token includes the subject (user's email), the issuer, the issue date,
     * and the expiration date based on the configured validity period.
     * The token is signed using the HS256 algorithm.</p>
     *
     * @param email The user's email to set as the token's subject.
     * @return A signed and encoded JWT token.
     */
    public String createAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuer("global-logic")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Verifies if a JWT token is valid.
     *
     * <p>Attempts to parse the token using the secret key. If the token has expired or is invalid,
     * it catches the corresponding exceptions and logs an error message.</p>
     *
     * @param token The JWT token to validate.
     * @return {@code true} if the token is valid; {@code false} if it has expired or is invalid.
     */
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired", ex);
            return false;
        } catch (JwtException | IllegalArgumentException ex) {
            log.error("JWT Exception: {0}", ex);
            return false;
        }
    }

    /**
     * Retrieves the subject (email) from a JWT token.
     *
     * <p>The subject is the authenticated user's email. If the token is valid,
     * it extracts the "subject" field from the token's body.</p>
     *
     * @param token The JWT token from which the subject will be extracted.
     * @return The subject of the token.
     */
    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
