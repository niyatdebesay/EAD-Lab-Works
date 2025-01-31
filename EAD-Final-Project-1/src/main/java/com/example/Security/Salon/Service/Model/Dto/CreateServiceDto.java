package com.example.Security.Salon.Service.Model.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateServiceDto {

    @NotNull
    private String name;

    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private int no_of_employees;

    @NotNull
    @Pattern(regexp = "^(\\d{1,2})(hr|hrs|min|minutes)?(\\s?\\d{1,2}(min|minutes)?)?$",
            message = "Duration must be in the format like 30min, 1hr, 2hrs, 2:30min, or 45minutes")
    private String duration;

    @NotNull
    private UUID salonId;
}
