package com.example.Security.Salon.Service.Model;

//import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Appointment.Model.AppointmentService;
import com.example.Security.Salon.Salon.Model.Salon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private String duration;

    private int no_of_employees;

    @ManyToOne
    private Salon salon;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedAt;

    @OneToMany(mappedBy = "service")
    private List<AppointmentService> appointmentService;

    public Service(String name, String description, BigDecimal price, String duration, Salon salon, int no_of_employees) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.salon = salon;
        this.no_of_employees = no_of_employees;


    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = new java.util.Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new java.util.Date();
    }
}
