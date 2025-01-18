package com.replix.office.repository;

import com.replix.office.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    /*
    @Query("SELECT p FROM User u JOIN u.roles r JOIN r.permissions p WHERE u.id = :userId")
    Set<Permission> findPermissionsByUserId(@Param("userId") Long userId);
    
     */
}
