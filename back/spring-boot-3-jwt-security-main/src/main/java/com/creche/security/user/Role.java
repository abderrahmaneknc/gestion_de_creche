package com.creche.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.creche.security.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  PARENT_READ,
                  PARENT_UPDATE,
                  PARENT_DELETE,
                  PARENT_CREATE
          )
  ),
  PARENT(
          Set.of(
                  PARENT_READ,
                  PARENT_UPDATE,
                  PARENT_DELETE,
                  PARENT_CREATE
          )
  ),
  GUEST(
          Set.of(
                  GUEST_READ,
                  GUEST_UPDATE,
                  GUEST_DELETE,
                  GUEST_CREATE
          )
  ),
  CHILD(
          Set.of(
                  CHILD_READ,
                  CHILD_UPDATE,
                  CHILD_DELETE,
                  CHILD_CREATE
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
