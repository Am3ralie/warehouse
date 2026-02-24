package org.example.tables.Controllers;

import org.example.tables.Entities.User;
import org.example.tables.Repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.web.bind.annotation.RestController
public class ApiController {
    private final UserRepository userRepository;

    public ApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/getUser/{login}")
    public User getUser(@PathVariable String login){
        return userRepository.findByLogin(login);
    }
}
