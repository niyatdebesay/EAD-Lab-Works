package com.example.Security.Salon.Service.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponseDto {

    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private String duration;

    private String salonName;

    private UUID salonId;
}
