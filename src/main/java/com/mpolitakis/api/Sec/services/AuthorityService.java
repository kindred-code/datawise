package com.mpolitakis.api.Sec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpolitakis.api.Models.Authority;
import com.mpolitakis.api.repository.AuthorityRepository;

@Service
public class AuthorityService {

    private final AuthorityRepository roleRepository;

    @Autowired
    public AuthorityService(AuthorityRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Authority createRole(String roleName) {
        Authority role = new Authority(roleName);
        return roleRepository.save(role);
    }

    public Authority getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

  

   
}
