package com.example.Security.Salon.Salon.Model.Dto;

import com.example.Security.Salon.Salon.Model.Authorized;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalonResponseDto {
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

    @NotNull
    private Authorized authorized;

    @NotNull
    @UUID
    private java.util.UUID authorizedBy;

    private Date authorizedAt;


}
