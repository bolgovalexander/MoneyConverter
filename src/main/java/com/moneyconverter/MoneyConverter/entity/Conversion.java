package com.moneyconverter.MoneyConverter.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
public class Conversion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private CurrencyFond currencyFond;

    private Date dateConversion;

    private double currentCurrencyAmount;

    private double transferAmount;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CurrencyFond getCurrencyFond() {
        return currencyFond;
    }

    public void setCurrencyFond(CurrencyFond currencyFond) {
        this.currencyFond = currencyFond;
    }

    public Date getDateConversion() {
        return dateConversion;
    }

    public void setDateConversion(Date dateConversion) {
        this.dateConversion = dateConversion;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }




    public void setConversion(User user, CurrencyFond currencyFond, Date dateConversion, double currentCurrencyAmount, double transferAmount){
        this.user = user;
        this.currencyFond = currencyFond;
        this.dateConversion = dateConversion;
        this.currentCurrencyAmount = currentCurrencyAmount;
        this.transferAmount = transferAmount;
    }

    @Transient
    private double culcCurr;

    public void setCulcCurr(double culcCurr) {
        this.culcCurr = culcCurr;
    }

    public double getCulcCurr() {
        return this.culcCurr;
    }
}
