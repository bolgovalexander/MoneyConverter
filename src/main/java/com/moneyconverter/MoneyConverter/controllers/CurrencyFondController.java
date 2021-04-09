package com.moneyconverter.MoneyConverter.controllers;

import com.moneyconverter.MoneyConverter.entity.CurrencyFond;
import com.moneyconverter.MoneyConverter.entity.User;
import com.moneyconverter.MoneyConverter.repos.CurrencyFondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path="/currency")
    public String allCurrency(Map<String, Object> model) {
        Iterable<CurrencyFond> currencyFonds = currencyFondRepository.findAll();
        model.put("currencyFonds",currencyFonds);
        model.put("pageTitle", "Валюты");
        model.put("module", "currencyfondmod");
        return  "currencyFond";
    }

    @GetMapping(path="/currency/add")
    public String addCurrency(Map<String, Object> model) {
        model.put("pageTitle", "Добавление валюты");
        return  "addCurrencyFond";
    }

    @PostMapping(path="/currency/add")
    public String addCurrency(@RequestParam String name
            , @RequestParam String code,Map<String, Object> model) {
        CurrencyFond currencyFond = new CurrencyFond();
        currencyFond.setName(name);
        currencyFond.setCode(code);
        currencyFond.setStatus(false);
        currencyFondRepository.save(currencyFond);

        Iterable<CurrencyFond> currencyFonds = currencyFondRepository.findAll();
        model.put("currencyFonds",currencyFonds);
        model.put("pageTitle", "Валюты");
        model.put("module", "currencyFond");
        return "redirect:/currency";
    }


    @GetMapping(path="/currency/edit/{id}")
    public String editCurrency(@PathVariable(value = "id") long id, Map<String, Object> model) {
        if(!currencyFondRepository.existsById(id)){
            return "redirect:/currency";
        }
        Optional<CurrencyFond> currencyFond = currencyFondRepository.findById(id);
        ArrayList<CurrencyFond> res = new ArrayList<>();
        currencyFond.ifPresent(res::add);
        model.put("currencyFonds",res);
        model.put("pageTitle", "Редактирование валюты");
        return "editCurrencyFond";
    }

    @PostMapping(path="/currency/edit/{id}")
    public String updateCurrency(@PathVariable(value = "id") long id, @RequestParam String code, @RequestParam String name, @RequestParam(value = "status", required = false) String status,Map<String, Object> model) {
        if(!currencyFondRepository.existsById(id)){
            return "redirect:/currency";
        }
        CurrencyFond cur = currencyFondRepository.findById(id).orElseThrow();
        cur.setCode(code);
        cur.setName(name);
        if(status != null)
        {
            cur.setStatus(true);
        }
        else
        {
            cur.setStatus(false);
        }
        currencyFondRepository.save(cur);
        return "redirect:/currency";
    }

    @PostMapping(path="/currency/delete/{id}")
    public String deleteCurrency(@PathVariable(value = "id") long id,Map<String, Object> model) {
        if(!currencyFondRepository.existsById(id)){
            return "redirect:/currency";
        }
        CurrencyFond cur = currencyFondRepository.findById(id).orElseThrow();
        currencyFondRepository.delete(cur);
        return "redirect:/currency";
    }
}
