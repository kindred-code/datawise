package com.mpolitakis.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpolitakis.api.Models.Authority;



public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    
    Authority findByName(String name);
    
}