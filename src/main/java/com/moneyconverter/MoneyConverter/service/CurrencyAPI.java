package com.moneyconverter.MoneyConverter.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class CurrencyAPI {
    private static RestTemplate restTemplate = new RestTemplate();
    private static final String key = "fb2e19bf90432dd02a0efbe5";

    public static double getCurrency(String code){
        double currencyValue = 0.0;
        String response = restTemplate.getForObject("https://v6.exchangerate-api.com/v6/"+key+"/latest/"+code, String.class);
        JSONObject currencyJsonObj;
        try {
            currencyJsonObj = new JSONObject(response);
            //System.out.println(currencyJsonObj.getJSONObject("conversion_rates").getString("RUB"));
            int i = (int) Math.round(Double.parseDouble(currencyJsonObj.getJSONObject("conversion_rates").getString("RUB")) * 100);
            currencyValue = (double) i / 100.0;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currencyValue;
    }
}
