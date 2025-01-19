package com.replix.office.repository;

import com.replix.office.models.Permission;
import com.replix.office.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("    SELECT p\n" +
            "    FROM Permission p\n" +
            "    JOIN RolePermission rp ON p.id = rp.permission.id\n" +
            "    JOIN Role r ON rp.role.id = r.id\n" +
            "    JOIN UserRole ur ON r.id = ur.role.id\n" +
            "    JOIN User u ON ur.user.id = u.id\n" +
            "    WHERE u.id = :userId")
    List<Permission> findPermissionsByUserId(@Param("userId") Integer userId);
/*
    SELECT p
    FROM Permission p
    JOIN RolePermission rp ON p.id = rp.permission.id
    JOIN Role r ON rp.role.id = r.id
    JOIN UserRole ur ON r.id = ur.role.id
    JOIN User u ON ur.user.id = u.id
    WHERE u.id = :userId

    SELECT p.id, p.permission_name, p.description
    FROM user u
    JOIN user_role ur ON u.id = ur.user_id
    JOIN role r ON ur.role_id = r.id
    JOIN role_permission rp ON r.id = rp.role_id
    JOIN permission p ON rp.permission_id = p.id
    WHERE u.id = ?
*/

}
