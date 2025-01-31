package com.example.Security.Salon.Role.Repository;

import com.example.Security.Salon.Role.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
     Role findByName(String name);
}
