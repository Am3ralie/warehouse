package org.example.tables.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.tables.configs.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    private final AuthService authService;

    public RedirectController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/endpointService")
    public String endpointService(HttpServletRequest request){
        return "redirect:"+authService.getUrl(request);
    }
}
