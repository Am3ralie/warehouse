package org.example.tables.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    ADMIN("ADMIN"),
    STOREKEEPER("STOREKEEPER");
    private final String permission;
}