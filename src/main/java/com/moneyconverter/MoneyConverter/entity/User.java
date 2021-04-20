package com.moneyconverter.MoneyConverter.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле Имя не может быть пустым")
    private String name;

    public Set<Conversion> getConversions() {
        return conversions;
    }

    public void setConversions(Set<Conversion> conversions) {
        this.conversions = conversions;
    }

    @NotBlank(message = "Поле Фамилия не может быть пустым")
    private String surName;

    @NotBlank(message = "Поле Отчество не может быть пустым")
    private String secondName;

    @NotBlank(message = "Поле Логин не может быть пустым")
    private String username;

    @NotBlank(message = "Поле Пароль не может быть пустым")
    private String password;

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Transient
    @NotBlank(message = "Поле Подтверждение пароля не может быть пустым")
    private String password2;

    private boolean active;

    @Email(message = "Некорректный Email")
    @NotBlank(message = "Поле Email не может быть пустым")
    private String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

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

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPerson(String name, String surName, String secondName, String email) {
        this.name = name;
        this.surName = surName;
        this.secondName = secondName;
        this.email = email;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Conversion> conversions;

    public Set<Conversion> getConversion(){
        return conversions;
    }

    public void setConversion(Set<Conversion> conversions) {
        this.conversions = conversions;
    }
}
