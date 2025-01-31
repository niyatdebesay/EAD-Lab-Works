package com.example.Security.Salon.Salon.Model;


import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.User.Model.User;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Salon {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")

    private UUID id;

    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String openingHours;
    private String image;
    private float rating;

    @Enumerated(EnumType.STRING)
    private Authorized authorized;


   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "authorizedBy")
   private User generalAdmin;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @Timestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date authorizedAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedAt;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<Service> services;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToOne()
    @JoinColumn(name= "created_by" , nullable = false, unique = true)
    private User user;

    public Salon(String name, String description, String address, String phoneNumber, String openingHours, String image, Authorized authorized, User user) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.openingHours = openingHours;
        this.image = image;
        this.authorized = authorized;
        this.user = user;
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



