package br.edu.utfpr.pb.pw26s.server.security;

import br.edu.utfpr.pb.pw26s.server.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private AuthUserService authUserService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   ApplicationContext ctx) {
        this.authenticationManager = authenticationManager;
        this.authUserService = ctx.getBean(AuthUserService.class);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        try {
            User credentials =
                    new ObjectMapper().readValue(request.getInputStream(), User.class);
            User user = (User) authUserService.loadUserByUsername(credentials.getUsername());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            user.getAuthorities()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        // String token = JWT
    }

    @Override
    protected AuthenticationSuccessHandler getSuccessHandler() {
        return super.getSuccessHandler();
    }
}
