package com.moneyconverter.MoneyConverter.controllers;

import com.moneyconverter.MoneyConverter.entity.Conversion;
import com.moneyconverter.MoneyConverter.entity.CurrencyFond;
import com.moneyconverter.MoneyConverter.entity.User;
import com.moneyconverter.MoneyConverter.repos.ConversionRepository;
import com.moneyconverter.MoneyConverter.repos.UserRepository;
import com.moneyconverter.MoneyConverter.repos.CurrencyFondRepository;
import com.moneyconverter.MoneyConverter.service.CurrencyAPI;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ConversionController {

    private final ConversionRepository conversionRepository;
    private final UserRepository userRepository;
    private final CurrencyFondRepository currencyFondRepository;

    public ConversionController(ConversionRepository conversionRepository, UserRepository userRepository, CurrencyFondRepository currencyFondRepository) {
        this.conversionRepository = conversionRepository;
        this.userRepository = userRepository;
        this.currencyFondRepository = currencyFondRepository;
    }

    @GetMapping(path = "/conversion")
    public String allConversion(HttpServletRequest request, Map<String, Object> model) {
        User userFromDB = userRepository.findByUsername(request.getUserPrincipal().getName());
        Set<Conversion> conversionSet = userFromDB.getConversion();
        for (Conversion s : conversionSet) {
            int i = (int) Math.round(s.getTransferAmount() / s.getCurrentCurrencyAmount() * 100);
            s.setCulcCurr((double) i / 100.0);
        }
        List<Conversion> sortedList = new ArrayList<>(conversionSet);
        sortedList.sort(Comparator.comparing(Conversion::getDateConversion));
        Collections.reverse(sortedList);
        model.put("conversions", sortedList);
        model.put("pageTitle", "Переводы");
        model.put("module", "conversion");
        return "conversion";
    }

    @GetMapping(path = "/conversion/make")
    public String addCurrency(Map<String, Object> model) {
        model.put("currency", getCurrencies());
        model.put("pageTitle", "Конвертация валюты");
        return "makeConversion";
    }

    @PostMapping(path = "/conversion/make")
    public String addCurrency(HttpServletRequest request, @RequestParam(required=false) Long currencyFond, @RequestParam String transferAmount, Map<String, Object> model) {
        if (currencyFond == null || !currencyFondRepository.existsById(currencyFond)) {
            model.put("currency", getCurrencies());
            model.put("pageTitle", "Конвертация валюты");
            model.put("mes", "Укажите корректную валюту!");
            return "makeConversion";
        }
        if(!isDouble(transferAmount)){
            model.put("currency", getCurrencies());
            model.put("pageTitle", "Конвертация валюты");
            model.put("mes", "Введите корректную сумму!");
            return "makeConversion";
        }

        User user = userRepository.findByUsername(request.getUserPrincipal().getName());
        Date date = new Date();
        CurrencyFond currency = currencyFondRepository.findById(currencyFond).orElseThrow();
        currency.setCurrentCurrencyAmount(CurrencyAPI.getCurrency(currency.getCode()));
        Conversion conversion = new Conversion();
        conversion.setConversion(user, currency, date, currency.getCurrentCurrencyAmount(), Double.parseDouble(transferAmount));
        conversionRepository.save(conversion);


        return "redirect:/conversion";
    }

    @PostMapping("/conversion/filter")
    public String filter(HttpServletRequest request, @RequestParam String filter, Map<String, Object> model) {
        User userFromDB = userRepository.findByUsername(request.getUserPrincipal().getName());
        Set<Conversion> conversionSet;
        if (filter != null && !filter.isEmpty()) {
            conversionSet = userFromDB.getConversion().stream().filter(c -> c.getDateConversion().getTime() > new Date().getTime()).collect(Collectors.toSet());;//Collections.singleton(conversionRepository.findAll (filter.trim()));
        } else {
            conversionSet = userFromDB.getConversion();
        }
        for (Conversion s : conversionSet) {
            int i = (int) Math.round(s.getTransferAmount() / s.getCurrentCurrencyAmount() * 100);
            s.setCulcCurr((double) i / 100.0);
        }
        List<Conversion> sortedList = new ArrayList<>(conversionSet);
        sortedList.sort((o1, o2) -> o1.getDateConversion().compareTo(o2.getDateConversion()));
        Collections.reverse(sortedList);
        model.put("conversions", sortedList);
        model.put("pageTitle", "Переводы");
        model.put("module", "conversion");
        return "conversion";
    }

    private List<CurrencyFond> getCurrencies(){
        Iterable<CurrencyFond> currency = currencyFondRepository.findAll();
        List<CurrencyFond> currencyInBank = new ArrayList<>();
        if (currency != null) {
            currency.forEach(c -> {
                if (c.getStatus() == true) {
                    c.setCurrentCurrencyAmount(CurrencyAPI.getCurrency(c.getCode()));
                    currencyInBank.add(c);
                }
            });
        }
        return currencyInBank;
    }

    private static boolean isDouble(String string)
    {
        try
        {
            Double.parseDouble(string);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}
