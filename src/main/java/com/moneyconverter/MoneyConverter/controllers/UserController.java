package com.moneyconverter.MoneyConverter.controllers;

import com.moneyconverter.MoneyConverter.entity.Role;
import com.moneyconverter.MoneyConverter.entity.User;
import com.moneyconverter.MoneyConverter.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        return  "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String,Object> model) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null){
            model.put("mes", "Такой пользователь уже существует!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        System.out.println(userRepository.toString());
        userRepository.save(user);
        return  "redirect:/login";
    }

    @PostMapping(path="/users/add")
    public String addNewUser (@RequestParam String name
            , @RequestParam String email,Map<String, Object> model) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);

        Iterable<User> users = userRepository.findAll();
        System.out.println(users);
        model.put("users",users);
        model.put("pageTitle", "Главная страница");
        return "redirect:/users";
    }

    @GetMapping(path="/users")
    public String allUsers(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();
        System.out.println(users);
        model.put("users",users);
        model.put("pageTitle", "Главная страница");
        return "users";
    }
}
