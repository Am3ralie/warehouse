package org.example.tables.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tables.Entities.User;
import org.example.tables.Repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final AuthService authService;

    public AuthFilter(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String login = authService.getLoginFromCookie(request);
        User user = userRepository.findByLogin(login);
        if (login!=null && user!=null){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    user.getLogin(),
                    null,
                    user.getRole().getAuthorities()));
        } else {
        SecurityContextHolder.clearContext();
    }
    try {
        filterChain.doFilter(request, response);
    } catch (ServletException | IOException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}
}