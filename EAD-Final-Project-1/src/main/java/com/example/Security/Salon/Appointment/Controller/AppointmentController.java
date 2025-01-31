package com.example.Security.Salon.Appointment.Controller;

import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Appointment.Model.Dto.AppointmentResponseDto;
import com.example.Security.Salon.Appointment.Model.Dto.CreateAppointmentDTO;
import com.example.Security.Salon.Appointment.Service.AppointmentService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.baseUrl}/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody CreateAppointmentDTO createAppointmentDTO) {
        try {
            Appointment appointment = appointmentService.addAppointment(createAppointmentDTO);
            AppointmentResponseDto responseDto = new AppointmentResponseDto(
                    appointment.getId(),
                    appointment.getDateTime(),
                    appointment.getStatus(),
                    appointment.getUser(),
                    appointment.getAppointmentServices()
            );
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointment(@PathVariable UUID id) {
        try {
            Appointment appointment = appointmentService.getAppointment(id);
            AppointmentResponseDto responseDto = new AppointmentResponseDto(
                    appointment.getId(),
                    appointment.getDateTime(),
                    appointment.getStatus(),
                    appointment.getUser(),
                    appointment.getAppointmentServices()

            );
            return ResponseEntity.ok(responseDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('SALON_ADMIN')")
    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsForSalon(@PathVariable UUID salonId) {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointmentsForSalon(salonId);
            List<AppointmentResponseDto> responseDtos = appointments.stream().map(appointment -> new AppointmentResponseDto(
                    appointment.getId(),
                    appointment.getDateTime(),
                    appointment.getStatus(),
                    appointment.getUser(),
                    appointment.getAppointmentServices()

            )).toList();
            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/rating")
    public ResponseEntity<Void> addRatingToAppointment(@PathVariable UUID id, @RequestParam int rating) {
        try {
            appointmentService.addRatingToAppointment(id, rating);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
