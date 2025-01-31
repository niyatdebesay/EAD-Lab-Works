package com.example.Security.Salon.Appointment.Model.Dto;

import com.example.Security.Salon.Appointment.Model.AppointmentService;
import com.example.Security.Salon.Appointment.Model.Status;
import com.example.Security.Salon.User.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data

@NoArgsConstructor
public class AppointmentResponseDto {

    private UUID appointmentId;
    private Date dateTime;
    private Status status;
    private User user;
    private List<AppointmentService> appointmentsServices;


    public AppointmentResponseDto(UUID appointmentId, Date dateTime, Status status, User user, List<AppointmentService> appointmentsServices) {
        this.appointmentId = appointmentId;
        this.dateTime = dateTime;
        this.status = status;
        this.user = user;
        this.appointmentsServices = appointmentsServices;
    }


}
