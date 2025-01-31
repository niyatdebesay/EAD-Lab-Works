package com.example.Security.Salon.Salon.Model.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalonDto {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 100, message = "Salon name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?\\s?-?\\d{3}\\s?-?\\d{4}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotBlank(message = "Opening hours are mandatory")
    private String openingHours;

    @Size(max = 255, message = "Image URL is too long")
    private String image;

    @UUID
    private java.util.UUID createdById;



}
