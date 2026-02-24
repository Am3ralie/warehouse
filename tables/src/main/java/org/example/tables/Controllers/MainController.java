package org.example.tables.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tables.Entities.Category;
import org.example.tables.Entities.User;
import org.example.tables.Entities.Warehouse;
import org.example.tables.Forms.LoginForm;
import org.example.tables.Repositories.CategoryRepository;
import org.example.tables.Repositories.UserRepository;
import org.example.tables.Repositories.WareHouseRepository;
import org.example.tables.configs.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final WareHouseRepository wareHouseRepository;
    private final CategoryRepository categoryRepository;

    public MainController(UserRepository userRepository, AuthService authService, WareHouseRepository wareHouseRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.wareHouseRepository = wareHouseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/storage")
    public String storage(Model model, HttpServletRequest request) {
        List<Warehouse> warehouses = wareHouseRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("warehouse",warehouses);
        model.addAttribute("categories",categories);
        String nameUser = authService.getLoginFromCookie(request);
        User user = userRepository.findByLogin(nameUser);
        model.addAttribute("user",user);
        return "storage";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("login_form", new LoginForm());
        return "login";
    }

    @PostMapping("/")
    public String login(@ModelAttribute("login_form") LoginForm loginForm, HttpServletResponse response, Model model) {
        User user = userRepository.findByLogin(loginForm.getLogin());
        if (user == null || !user.getPassword().equals(loginForm.getPassword())) {
            model.addAttribute("err", "Неверный логин или пароль");
            model.addAttribute("login_form", new LoginForm());
            return "login";
        }
        authService.setCookie(loginForm.getLogin(), response);
        return "redirect:/storage";
    }
    @PostMapping("/equipment/del/{id}")
    public String deleteWareHouse(@PathVariable int id) {
        wareHouseRepository.deleteById(id);
        return "redirect:/storage";
    }

    @Transactional
    @PostMapping("/category/del/{id}")
    public String deleteCategory(@PathVariable int id) {
        wareHouseRepository.deleteByCategory(categoryRepository.findById(id));
        categoryRepository.deleteById(id);
        return "redirect:/storage";
    }


}
