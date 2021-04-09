package com.moneyconverter.MoneyConverter.controllers;

import com.moneyconverter.MoneyConverter.entity.Conversion;
import com.moneyconverter.MoneyConverter.repos.ConversionRepository;
import com.moneyconverter.MoneyConverter.repos.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ConversionController {

    private final ConversionRepository conversionRepository;
    private final UserRepository userRepository;

    public ConversionController(ConversionRepository conversionRepository, UserRepository userRepository) {
        this.conversionRepository = conversionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(path="/conversion")
    public String allConversion(HttpServletRequest request, Map<String, Object> model) {
        Long userId = userRepository.findByUsername(request.getUserPrincipal().getName()).getId();
        if (request.isUserInRole("USER")) {
            System.out.println("USER");
        }
        else{
            System.out.println("ADMIN");
        }
        Iterable<Conversion> conversions = (Iterable<Conversion>) conversionRepository.findByUserId(userId);
        model.put("conversions",conversions);
        model.put("pageTitle", "Переводы");
        model.put("module", "conversion");
        return  "conversion";
    }
}
