package com.moneyconverter.MoneyConverter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class CurrencyFond {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле Код не может быть пустым")
    @Size(min = 3, max = 3, message = "Поле должно содержать 3 символа")
    private String code;

    @NotBlank(message = "Поле Название валюты не может быть пустым")
    private String name;

    private Boolean status;

    @Transient
    private double currentCurrencyAmount;

    public void setCurrentCurrencyAmount(double currentCurrencyAmount) {
        this.currentCurrencyAmount = currentCurrencyAmount;
    }

    public double getCurrentCurrencyAmount() {
        return this.currentCurrencyAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public void setCurrency(Long id, String code, String name, Boolean status){
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
    }


}
