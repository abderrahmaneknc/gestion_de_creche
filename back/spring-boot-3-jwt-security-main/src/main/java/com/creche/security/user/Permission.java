package com.creche.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    PARENT_READ("parent:read"),
    PARENT_UPDATE("parent:update"),
    PARENT_CREATE("parent:create"),
    PARENT_DELETE("parent:delete"),
    GUEST_READ("guest:read"),
    GUEST_UPDATE("guest:update"),
    GUEST_CREATE("guest:create"),
    GUEST_DELETE("guest:delete"),
    CHILD_READ("child:read"),
    CHILD_UPDATE("child:update"),
    CHILD_CREATE("child:create"),
    CHILD_DELETE("child:delete")

    ;

    @Getter
    private final String permission;
}
