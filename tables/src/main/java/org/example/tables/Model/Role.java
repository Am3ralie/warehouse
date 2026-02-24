package org.example.tables.Model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Role {
    STOREKEEPER(Set.of(Permission.STOREKEEPER)),
    ADMIN(Set.of(Permission.STOREKEEPER,Permission.ADMIN));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Permission permission : getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
        }
        return authorities;
    }
}
