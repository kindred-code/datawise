package com.mpolitakis.datawise.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mpolitakis.datawise.user.Permission.ADMIN_CREATE;
import static com.mpolitakis.datawise.user.Permission.ADMIN_DELETE;
import static com.mpolitakis.datawise.user.Permission.ADMIN_READ;
import static com.mpolitakis.datawise.user.Permission.ADMIN_UPDATE;
import static com.mpolitakis.datawise.user.Permission.CLIENT_READ;
import static com.mpolitakis.datawise.user.Permission.CLIENT_CREATE;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  CLIENT_CREATE,
                  CLIENT_READ
                  
          )
  ),
  CLIENT(
          Set.of(
                  CLIENT_READ,
                  CLIENT_CREATE
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
