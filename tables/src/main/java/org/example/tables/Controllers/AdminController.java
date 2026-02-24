package org.example.tables.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tables.Entities.User;
import org.example.tables.Forms.EditForm;
import org.example.tables.Forms.RegForm;
import org.example.tables.Model.Role;
import org.example.tables.Repositories.UserRepository;
import org.example.tables.configs.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private final UserRepository userRepository;
    private final AuthService authService;

    public AdminController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request){
        if(request.getParameter("err_reg")!=null){
            model.addAttribute("err_reg","Логин занят");
        }
        if(request.getParameter("err_edit")!=null){
            model.addAttribute("err_edit","Логин занят");
        }
        model.addAttribute("edit_form",new EditForm());
        model.addAttribute("user_logins",getAllLogins());
        model.addAttribute("reg_form",new RegForm());
        return "admin";
    }

    private List<String> getAllLogins(){
        List<User> users = userRepository.findAll();
        List<String> logins = new ArrayList<>();
        for (User user : users) {
            logins.add(user.getLogin());
        }
        return logins;
    }

    @PostMapping("/admin/reg")
    public String register(@ModelAttribute("user_form") RegForm regForm){
        if (userRepository.findByLogin(regForm.login)!=null){
            return "redirect:/admin?err_reg=true";
        }
        User user = new User(regForm.FIO,regForm.login,regForm.password, Role.STOREKEEPER);
        userRepository.save(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/edit")
    public String editUser(@ModelAttribute("user_form") EditForm editForm, HttpServletRequest request, HttpServletResponse response){
        User user = userRepository.findByLogin(editForm.oldLogin);
        String yourLogin = authService.getLoginFromCookie(request);
        if (userRepository.findByLogin(editForm.login)!=null && !yourLogin.equals(editForm.oldLogin)){
            return "redirect:/admin?err_edit=true";
        }
        user.setName(editForm.FIO);
        user.setLogin(editForm.login);
        user.setPassword(editForm.password);
        userRepository.save(user);
        if (yourLogin.equals(editForm.getOldLogin())){
            authService.setCookie(user.getLogin(),response);
        }
        return "redirect:/admin";
    }
}
