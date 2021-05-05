package com.moneyconverter.MoneyConverter.controllers;

import com.moneyconverter.MoneyConverter.entity.Role;
import com.moneyconverter.MoneyConverter.entity.User;
import com.moneyconverter.MoneyConverter.repos.UserRepository;
import com.moneyconverter.MoneyConverter.service.CurrencyAPI;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@Controller
public class HomeController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        String[] currencyNameArray = new String[]{"USD", "EUR", "GBP"};
        Map<String, String> map = new HashMap<String, String>();

        for (int i = 0; i < currencyNameArray.length; i++) {
            map.put(currencyNameArray[i], Double.toString(CurrencyAPI.getCurrency(currencyNameArray[i])));
        }
        model.addAttribute("USD", Double.parseDouble(map.get("USD")));
        model.addAttribute("EUR", Double.parseDouble(map.get("EUR")));
        model.addAttribute("GBP", Double.parseDouble(map.get("GBP")));
        model.addAttribute("pageTitle", "Главная страница");
        return "index";
    }

    @GetMapping("/registration")
    public String registration(User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.put("passMessage", "Вы ввели разные пароли");
            return "registration";
        }
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.put("mes", "Такой пользователь уже существует!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";

    }
}

