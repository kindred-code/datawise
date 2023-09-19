package com.mpolitakis.datawise.user;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mpolitakis.datawise.Models.Authority;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  private String username;
  private String email;
  private String password;
 

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
  private final List<Authority> authorities = new ArrayList<>();;
    
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

      List<GrantedAuthority> listRole = new ArrayList<GrantedAuthority>();
      
    for (Authority role : authorities) {
      listRole.add(new SimpleGrantedAuthority(role.getName()));
    }
        System.out.println(listRole);
        return listRole;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
