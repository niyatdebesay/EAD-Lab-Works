package com.example.Security.Salon.Service.Controller;

import com.example.Security.Salon.Service.Model.Dto.CreateServiceDto;
import com.example.Security.Salon.Service.Model.Dto.UpdateServiceDto;
import com.example.Security.Salon.Service.Model.Dto.ServiceResponseDto;
import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.Service.Services.ServiceService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PreAuthorize("hasRole('SALON_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createService(@Validated @RequestBody CreateServiceDto dto) {
        try {
            Service service = serviceService.addService(dto);
            ServiceResponseDto responseDto = new ServiceResponseDto(
                    service.getId(),
                    service.getName(),
                    service.getDescription(),
                    service.getPrice(),
                    service.getDuration(),
                    service.getSalon().getName(),
                    service.getSalon().getId()
            );
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Service already exists with the same name in this salon.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred while creating the service.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('SALON_ADMIN')")
    @GetMapping("/salon/{salonId}")
    public ResponseEntity<?> getServicesForSalon(@PathVariable UUID salonId) {
        try {
            List<Service> services = serviceService.getAllServices(salonId);
            List<ServiceResponseDto> responseDtos = services.stream().map(service -> new ServiceResponseDto(
                    service.getId(),
                    service.getName(),
                    service.getDescription(),
                    service.getPrice(),
                    service.getDuration(),
                    service.getSalon().getName(),
                    service.getSalon().getId()
            )).toList();
            return new ResponseEntity<>(responseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred while fetching services for the salon.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('SALON_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable UUID id) {
        try {
            Service service = serviceService.findServiceById(id);
            ServiceResponseDto responseDto = new ServiceResponseDto(
                    service.getId(),
                    service.getName(),
                    service.getDescription(),
                    service.getPrice(),
                    service.getDuration(),
                    service.getSalon().getName(),
                    service.getSalon().getId()
            );
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Service not found with the provided ID.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred while fetching the service.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('SALON_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable UUID id, @Validated @RequestBody UpdateServiceDto dto) {
        try {
            Service service = serviceService.updateService(id, dto);
            ServiceResponseDto responseDto = new ServiceResponseDto(
                    service.getId(),
                    service.getName(),
                    service.getDescription(),
                    service.getPrice(),
                    service.getDuration(),
                    service.getSalon().getName(),
                    service.getSalon().getId()
            );
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Service not found with the provided ID. Update failed.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred while updating the service.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('SALON_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable UUID id) {
        try {
            serviceService.deleteService(id);
            return new ResponseEntity<>("Service deleted successfully.", HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Service not found with the provided ID. Delete failed.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred while deleting the service.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
