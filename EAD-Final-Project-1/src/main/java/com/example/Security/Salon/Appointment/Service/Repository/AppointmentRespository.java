package com.example.Security.Salon.Appointment.Service.Repository;

import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Appointment.Model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRespository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findBySalon_Id(UUID salonId) throws Exception;
    List<Appointment> findBySalon_IdAndStatus(UUID salonId, Status status) throws Exception;
    Optional<Appointment> findBySalon_IdAndDateTime( UUID salonId, Date dateTime);
}
