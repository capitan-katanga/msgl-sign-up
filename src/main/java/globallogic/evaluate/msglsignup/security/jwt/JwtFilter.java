package globallogic.evaluate.msglsignup.security.jwt;

import globallogic.evaluate.msglsignup.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * JWT filter that runs once per request to validate and manage authentication based on a JWT token.
 *
 * <p>This class extends {@link OncePerRequestFilter} and verifies if the HTTP request contains a valid JWT
 * token in the "Authorization" header. If the token is valid, it sets the authentication context with
 * the corresponding user details.
 *
 * <h2>How it works:</h2>
 * <ol>
 *   <li>Retrieves the JWT token from the "Authorization" header.</li>
 *   <li>Validates the token using {@link JwtProvider}.</li>
 *   <li>If valid, sets the security context with the authenticated user’s details.</li>
 * </ol>
 *
 * @see OncePerRequestFilter
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Constructor that injects the necessary dependencies.
     *
     * @param jwtProvider        The JWT provider used to validate and manage tokens.
     * @param userDetailsService The service to load user details.
     */
    @Autowired
    public JwtFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filter that intercepts HTTP requests to validate the JWT token.
     *
     * <p>If the token is valid, it sets the authentication context. Then, it continues with the filter chain
     * to process the request.</p>
     *
     * @param request     The incoming HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain to continue processing the request.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        getBearerToken(request).ifPresent(
                token -> {
                    if (jwtProvider.isValidToken(token)) {
                        setAuthenticationContext(token, request);
                    }
                }
        );
        filterChain.doFilter(request, response);
    }

    /**
     * Retrieves the JWT token from the "Authorization" header of the HTTP request.
     *
     * <p>The header must start with the word "Bearer" followed by the token. If the header
     * is missing or has an incorrect format, it returns an empty {@link Optional}.</p>
     *
     * @param request The incoming HTTP request.
     * @return An {@link Optional} containing the JWT token if present and correctly formatted;
     * otherwise, an empty {@link Optional}.
     */
    private Optional<String> getBearerToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")) {
            return Optional.of(header.replace("Bearer ", ""));
        }
        logger.error("Authorization header null or not start with Bearer");
        return Optional.empty();
    }

    /**
     * Sets the authentication context with the authenticated user’s details.
     *
     * <p>Retrieves the subject (the user's email) from the token and loads the user's details
     * from {@link UserDetailsServiceImpl}. Then, it sets the authentication in the security context.</p>
     *
     * @param token   The valid JWT token.
     * @param request The current HTTP request.
     */
    private void setAuthenticationContext(String token, HttpServletRequest request) {
        var emailSubject = jwtProvider.getSubject(token);
        var userDetails = userDetailsService.loadUserByUsername(emailSubject);
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
