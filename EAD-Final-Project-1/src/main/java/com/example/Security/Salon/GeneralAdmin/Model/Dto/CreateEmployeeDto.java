package com.example.Security.Salon.GeneralAdmin.Model.Dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateEmployeeDto {
    @NotNull
    private UUID salonId;


    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Role cannot be blank")
    private String role;

    @NotNull(message = "Specialties must be specified")
    @Size(min = 1, message = "At least one specialty must be selected")
    private List<String> specialties;

    @Lob
    private String availability;

    @Min(value = 0, message = "Experience cannot be negative")
    private int experience;

    private String image;

}
