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

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getBearerToken(request).ifPresent(
                token -> {
                    if (jwtProvider.isValidToken(token)) {
                        setAuthenticationContext(token, request);
                    }
                }
        );
        filterChain.doFilter(request, response);
    }

    private Optional<String> getBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")) {
            return Optional.of(header.replace("Bearer ", ""));
        }
        logger.error("Authorization header null or not start with Bearer");
        return Optional.empty();
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        var emailSubject = jwtProvider.getSubject(token);
        var userDetails = userDetailsService.loadUserByUsername(emailSubject);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
