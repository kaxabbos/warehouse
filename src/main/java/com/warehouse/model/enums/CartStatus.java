package com.warehouse.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CartStatus {
    ISSUED("Оформляется"),
    WAITING("Ожидание"),
    REFUSED("Отказано"),
    APPROVED("Одобрено");

    private final String name;
}

