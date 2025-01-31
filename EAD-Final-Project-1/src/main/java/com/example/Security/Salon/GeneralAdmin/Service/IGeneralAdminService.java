package com.example.Security.Salon.GeneralAdmin.Service;

import com.example.Security.Salon.Exception.ResourceNotFoundException;

import com.example.Security.Salon.Salon.Model.Authorized;
import com.example.Security.Salon.Salon.Model.Salon;
import jakarta.mail.MessagingException;

import java.io.IOException;

import java.util.List;
import java.util.UUID;

public interface IGeneralAdminService {
    Salon authorizeASalon(UUID salonId, UUID adminId) throws ResourceNotFoundException, IllegalAccessException, MessagingException, IOException;
    List<Salon> listAllSalons();
    List<Salon> findAllSalonsWithAuthorization(Authorized authorized);
}

