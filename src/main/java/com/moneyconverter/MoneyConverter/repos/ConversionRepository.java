package com.moneyconverter.MoneyConverter.repos;

import com.moneyconverter.MoneyConverter.entity.Conversion;
import org.springframework.data.repository.CrudRepository;

public interface ConversionRepository extends CrudRepository<Conversion, Integer> {
    Conversion findByUserId(Long userId);
}
