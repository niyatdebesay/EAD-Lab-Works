package com.example.Security.Salon.Appointment.Model;


import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.User.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private Date dateTime;

    @Column
    private int rating;

    @Column(nullable = true)
    private String comment;


    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AppointmentService> appointmentServices;


    @ManyToOne()
    @JoinColumn(name="salon_id")
    private Salon salon;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Appointment(Date dateTime, Status status, List<AppointmentService> appointmentServices, Salon salon, User user) {
        this.dateTime = dateTime;
        this.status = status;
        this.appointmentServices = appointmentServices;
        this.salon = salon;
        this.user = user;
    }







}
