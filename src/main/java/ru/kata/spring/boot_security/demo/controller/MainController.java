package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import ru.kata.spring.boot_security.demo.security.SecurityUserDetails;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String authenticatedPage( Model model,Principal principal) {
        User user = userService.selectUser(principal.getName());
        model.addAttribute("userRole",user);

        return "user";
    }
    @GetMapping("/userOff")
    public String authenticatedPageOff( Model model,Principal principal) {
        User user = userService.selectUser(principal.getName());
        model.addAttribute("userRole",user);

        return "userOff";
    }
    @GetMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        List<User> allUser=userService.getAll();
        List<Role> roleall=userService.getRole();
        User user = userService.selectUser(principal.getName());

        model.addAttribute("userRole",user);

        model.addAttribute("users",allUser);
        model.addAttribute("allroles",roleall);
        model.addAttribute("name",principal);
        model.addAttribute("rolles",userService.getRole());
        model.addAttribute("newuser",new User());
        return "admin";
    }

    @PostMapping("/admin")
    public String newUserPost(@ModelAttribute("newuser") User user) {

        userService.newUser(user);

        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}")
    public String editUserPost(@ModelAttribute("user") User user){

        userService.editUser(user);

        return "redirect:/admin";
    }
    @DeleteMapping("/admin/{id}")
    public String deleteId(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
