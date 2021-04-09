package com.moneyconverter.MoneyConverter.controllers;

import org.json.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        final RestTemplate restTemplate = new RestTemplate();
        String[] currencyNameArray = new String[]{"USD", "EUR", "GBP"};
        Map<String, String> map = new HashMap<String, String>();

        for (int i = 0; i < currencyNameArray.length; i++) {
            String response = restTemplate.getForObject("https://api.exchangeratesapi.io/latest?base=" + currencyNameArray[i] + "&symbols=RUB", String.class);
            JSONObject currencyJsonObj = null;
            try {
                currencyJsonObj = new JSONObject(response);
                map.put(currencyNameArray[i], currencyJsonObj.getJSONObject("rates").getString("RUB"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        double USD = 0.0;
        double EUR = 0.0;
        double GBP = 0.0;
        try{
            USD = Math.round((Double.parseDouble(map.get("USD"))) * 100) / 100.0;
            EUR = Math.round((Double.parseDouble(map.get("EUR"))) * 100) / 100.0;
            GBP = Math.round((Double.parseDouble(map.get("GBP"))) * 100) / 100.0;
        }catch (Exception ex){

        }

        model.addAttribute("USD", USD);
        model.addAttribute("EUR", EUR);
        model.addAttribute("GBP", GBP);
        model.addAttribute("pageTitle", "Главная страница");
        return "index";
    }

}
