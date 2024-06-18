package edu.azati.marketservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("admin"),

    SELLER("seller"),

    WORKER("worker"),

    CUSTOMER("customer");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
