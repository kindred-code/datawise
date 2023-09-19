package com.mpolitakis.datawise.auth;



import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  
  private String username;
  private String email;
  private String password;
  private Set<String> authorities;
}
