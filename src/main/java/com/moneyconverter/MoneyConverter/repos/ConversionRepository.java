package com.moneyconverter.MoneyConverter.repos;

import com.moneyconverter.MoneyConverter.entity.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ConversionRepository extends JpaRepository<Conversion, Integer> {
    Conversion findByUserId(Long userId);
}
