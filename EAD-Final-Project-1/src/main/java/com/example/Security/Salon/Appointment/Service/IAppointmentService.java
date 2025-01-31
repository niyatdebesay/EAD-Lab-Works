package com.example.Security.Salon.Appointment.Service;

import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Appointment.Model.Dto.CreateAppointmentDTO;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface IAppointmentService {


    @Transactional
    Appointment addAppointment(CreateAppointmentDTO createAppointmentDTO) throws Exception;

    Appointment getAppointment(UUID id) throws ResourceNotFoundException;
    Appointment updateAppointment(UUID id, CreateAppointmentDTO createAppointmentDto);
    void deleteAppointment(UUID id) throws ResourceNotFoundException;
    List<Appointment> getAllAppointmentsForSalon(UUID salonId) throws Exception;
    String addRatingToAppointment(UUID appointmentID, int rating) throws Exception;
}
