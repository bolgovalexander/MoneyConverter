package com.moneyconverter.MoneyConverter.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Conversion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private CurrencyFond currencyFond;

    private Date dateConversion;

    private double currentCurrencyAmount;

    private double transferAmount;

    public void setConversion(Long id, User user, CurrencyFond currencyFond, Date dateConversion, double currentCurrencyAmount, double transferAmount){
        this.id = id;
        this.user = user;
        this.currencyFond = currencyFond;
        this.dateConversion = dateConversion;
        this.currentCurrencyAmount = currentCurrencyAmount;
        this.transferAmount = transferAmount;
    }
}
