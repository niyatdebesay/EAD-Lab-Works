package com.example.Security.Salon.Role.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Role {
    @Id

    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;


}
