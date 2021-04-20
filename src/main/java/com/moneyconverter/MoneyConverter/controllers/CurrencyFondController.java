package com.moneyconverter.MoneyConverter.controllers;

import com.moneyconverter.MoneyConverter.entity.CurrencyFond;
import com.moneyconverter.MoneyConverter.entity.User;
import com.moneyconverter.MoneyConverter.repos.CurrencyFondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
//@RequestMapping(path="/currency")
public class CurrencyFondController {
    private final CurrencyFondRepository currencyFondRepository;

    public CurrencyFondController(CurrencyFondRepository currencyFondRepository) {
        this.currencyFondRepository = currencyFondRepository;
    }

    @GetMapping(path = "/currency")
    public String allCurrency(Map<String, Object> model) {
        Iterable<CurrencyFond> currencyFonds = currencyFondRepository.findAll();
        model.put("currencyFonds", currencyFonds);
        model.put("pageTitle", "Валюты");
        model.put("module", "currencyfondmod");
        return "currencyFond";
    }

    @GetMapping(path = "/currency/add")
    public String addCurrency(CurrencyFond currencyFond, Map<String, Object> model) {
        model.put("pageTitle", "Добавление валюты");
        return "addCurrencyFond";
    }

    @PostMapping(path = "/currency/add")
    public String addCurrency(@Valid CurrencyFond currencyFond,
                              BindingResult bindingResult,
                              Model model) {
        model.addAttribute("pageTitle", "Редактирование валюты");
        if (bindingResult.hasErrors()) {
            return "addCurrencyFond";
        }
        CurrencyFond cf = currencyFondRepository.findByCode(currencyFond.getCode());
        if (cf != null) {
            model.addAttribute("mes", "Такая валюта уже существует!");
            return "addCurrencyFond";
        }
        currencyFond.setStatus(false);
        currencyFondRepository.save(currencyFond);
        return "redirect:/currency";
    }


    @GetMapping(path = "/currency/edit/{id}")
    public String editCurrency(@PathVariable(value = "id") long id, Map<String, Object> model, CurrencyFond currencyFond) {
        if (!currencyFondRepository.existsById(id)) {
            return "redirect:/currency";
        }
        Optional<CurrencyFond> cf = currencyFondRepository.findById(id);
        ArrayList<CurrencyFond> res = new ArrayList<>();
        cf.ifPresent(res::add);
        model.put("currencyFonds", res);
        model.put("pageTitle", "Редактирование валюты");
        return "editCurrencyFond";
    }

    @PostMapping(path = "/currency/edit/{id}")
    public String updateCurrency(@PathVariable(value = "id") long id, @Valid CurrencyFond currencyFond, BindingResult bindingResult, @RequestParam(value = "status", required = false) String status, Map<String, Object> model) {
        if (!currencyFondRepository.existsById(id)) {
            return "redirect:/currency";
        }
        if (bindingResult.hasErrors()) {
            return "editCurrencyFond";
        }
        CurrencyFond cur = currencyFondRepository.findById(id).orElseThrow();
        cur.setCode(currencyFond.getCode());
        cur.setName(currencyFond.getName());
        if (status != null) {
            cur.setStatus(true);
        } else {
            cur.setStatus(false);
        }
        currencyFondRepository.save(cur);
        return "redirect:/currency";
    }

    @PostMapping(path = "/currency/delete/{id}")
    public String deleteCurrency(@PathVariable(value = "id") long id, Map<String, Object> model) {
        if (!currencyFondRepository.existsById(id)) {
            return "redirect:/currency";
        }
        CurrencyFond cur = currencyFondRepository.findById(id).orElseThrow();
        currencyFondRepository.delete(cur);
        return "redirect:/currency";
    }


    @PostMapping("/currency/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<CurrencyFond> currencyFonds;

        if (filter != null && !filter.isEmpty()) {
            currencyFonds = currencyFondRepository.findByName(filter.trim());
        } else {
            currencyFonds = currencyFondRepository.findAll();
        }

        model.put("currencyFonds", currencyFonds);
        model.put("pageTitle", "Валюты");
        model.put("module", "currencyfondmod");
        return "currencyFond";
    }
}
