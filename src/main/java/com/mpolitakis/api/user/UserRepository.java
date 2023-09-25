package com.mpolitakis.api.user;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

  Optional<User> findById(Long userId);

  

}
