package com.example.Security.Salon.User.Model;
import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Role.Model.Role;
import com.example.Security.Salon.Salon.Model.Salon;
import jakarta.persistence.*;
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
public class User {
@Id
@GeneratedValue(generator = "UUID")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")

private UUID id;

private String firstName;
private String lastName;
@Column(unique = true, nullable = false)
private String username;
@Column(unique = true, nullable = false)
private String phoneNumber;
@Column(unique = true, nullable = false)
private String email;
@Column()
private String password;

@OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
private List<Appointment> appointments;

@ManyToOne
@JoinColumn(name="role_id" )
private Role role;

@CreationTimestamp
@Temporal(TemporalType.TIMESTAMP)
private java.util.Date createdAt;

@UpdateTimestamp
@Temporal(TemporalType.TIMESTAMP)
private java.util.Date updatedAt;

@ManyToOne(cascade = CascadeType.ALL)
private Salon salon;

public User(String firstName, String lastName, String username, String phoneNumber, String email, String password, Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password = password;
    this.role = role;


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

