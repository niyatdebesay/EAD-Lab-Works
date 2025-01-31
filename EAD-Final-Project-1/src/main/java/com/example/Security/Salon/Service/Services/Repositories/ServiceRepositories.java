package com.example.Security.Salon.Service.Services.Repositories;

import com.example.Security.Salon.Service.Model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepositories extends  JpaRepository<Service, UUID> {

    Optional<Service> findByNameAndSalonId(String name, UUID salonId);  // To check if the service already exists in a salon

}
