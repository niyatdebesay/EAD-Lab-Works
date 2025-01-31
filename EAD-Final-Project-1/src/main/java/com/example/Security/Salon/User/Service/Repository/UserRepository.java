package com.example.Security.Salon.User.Service.Repository;

import com.example.Security.Salon.Role.Model.Role;
import com.example.Security.Salon.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    User findByUsername(String username);

    User findByIdAndRole(UUID id, Role role);
}