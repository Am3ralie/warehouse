package org.example.tables.configs;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tables.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getLoginFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("login".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void setCookie(String phone, HttpServletResponse response) {
        Cookie cookie = new Cookie("login", phone);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String  getUrl(HttpServletRequest request) {
        if (isAuth(request)) return "/profile";
        return "/login";
    }

    private boolean isAuth(HttpServletRequest request) {
        String login = getLoginFromCookie(request);
        if (login==null || userRepository.findByLogin(login)==null) return false;
        return true;
    }
}
