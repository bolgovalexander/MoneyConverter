package com.moneyconverter.MoneyConverter.repos;

import com.moneyconverter.MoneyConverter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
