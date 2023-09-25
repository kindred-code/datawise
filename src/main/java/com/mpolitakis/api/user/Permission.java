package com.mpolitakis.api.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    CLIENT_CREATE("client:create"),
    CLIENT_READ("client:read"),
    ;

    @Getter
    private final String permission;
}
