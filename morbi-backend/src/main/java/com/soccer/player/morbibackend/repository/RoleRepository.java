package com.soccer.player.morbibackend.repository;

import com.soccer.player.morbibackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(Role.RoleName name);

    Boolean existsByRoleName(Role.RoleName roleName);
}
