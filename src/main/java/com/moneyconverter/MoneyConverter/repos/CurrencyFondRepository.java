package com.moneyconverter.MoneyConverter.repos;

import com.moneyconverter.MoneyConverter.entity.CurrencyFond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyFondRepository extends JpaRepository<CurrencyFond, String> {
    CurrencyFond findByCode(String code);
    List<CurrencyFond> findByName(String name);
    boolean existsById(long id);
    Optional<CurrencyFond> findById(long id);
}
