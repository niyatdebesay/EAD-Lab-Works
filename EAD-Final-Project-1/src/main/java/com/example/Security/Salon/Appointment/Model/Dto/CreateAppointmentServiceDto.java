package com.example.Security.Salon.Appointment.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentServiceDto {
    private UUID serviceId;
    private int durationInMinutes;
}
