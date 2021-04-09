package com.moneyconverter.MoneyConverter.repos;

import com.moneyconverter.MoneyConverter.entity.CurrencyFond;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CurrencyFondRepository extends CrudRepository<CurrencyFond, String> {

    boolean existsById(long id);

    Optional<CurrencyFond> findById(long id);
}
