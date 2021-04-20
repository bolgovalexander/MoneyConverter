package com.moneyconverter.MoneyConverter.controllers;

import com.moneyconverter.MoneyConverter.entity.CurrencyFond;
import com.moneyconverter.MoneyConverter.entity.Role;
import com.moneyconverter.MoneyConverter.entity.User;
import com.moneyconverter.MoneyConverter.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        model.addAttribute("pageTitle", "Пользователи");
        model.addAttribute("module", "users");
        return "users";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("editUser", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("module", "users");
        model.addAttribute("pageTitle", "Изменение роли пользователя");
        return "editUser";
    }

    @PostMapping
    public String userSave(
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);

        return "redirect:/users";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<User> users;

        if (filter != null && !filter.isEmpty()) {
            users = Collections.singleton(userRepository.findByUsername(filter.trim()));
        } else {
            users = userRepository.findAll();
        }

        model.put("userList", users);
        model.put("pageTitle", "Пользователи");
        model.put("module", "users");
        return "users";
    }
}
