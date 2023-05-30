package com.warehouse.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    ADMIN("Управляющий"),
    SELLER("Сотрудник"),
    BUYER("Менеджер");

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}

