package com.moneyconverter.MoneyConverter.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class CurrencyAPI {
    private static RestTemplate restTemplate = new RestTemplate();

    public static double getCurrency(String code){
        double currencyValue = 0.0;
        String response = restTemplate.getForObject("https://api.ratesapi.io/api/latest?base=" + code + "&symbols=RUB", String.class);
        JSONObject currencyJsonObj;
        try {
            currencyJsonObj = new JSONObject(response);
            int i = (int) Math.round(Double.parseDouble(currencyJsonObj.getJSONObject("rates").getString("RUB")) * 100);
            currencyValue = (double) i / 100.0;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currencyValue;
    }
}
