package com.mpolitakis.datawise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpolitakis.datawise.Models.Authority;



public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    
    Authority findByName(String name);
    
}