package com.sparta.coffeedeliveryproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRole {

    @Id
    private String role;

    public UserRole(String role) {
        this.role = role;
    }

}