package com.example.Security.Salon.Appointment.Model.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateAppointmentDTO {

    private UUID salonId;
    private UUID userId;
    private Date dateTime;
    private String status;
    private List<ServiceTimeDTO> serviceTimes;  // List of services and their scheduled times

    @Getter
    @Setter
    public static class ServiceTimeDTO {
        private UUID serviceId;
        private Date startTime;
        private Date endTime;
    }
}
