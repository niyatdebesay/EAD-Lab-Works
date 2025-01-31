package com.example.Security.Salon.Service.Services;

import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import com.example.Security.Salon.Service.Model.Dto.CreateServiceDto;
import com.example.Security.Salon.Service.Model.Dto.UpdateServiceDto;
import com.example.Security.Salon.Service.Model.Service;
import com.example.Security.Salon.Service.Services.Repositories.ServiceRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {


    private final ServiceRepositories serviceRepository;
    private final SalonService salonService;

    @Autowired
    public ServiceService(ServiceRepositories serviceRepository, SalonService salonService) {
        this.serviceRepository = serviceRepository;
        this.salonService = salonService;
    }



    public Service addService(CreateServiceDto createServiceDto) throws ResourceNotFoundException, IllegalAccessException {

        var salon = Optional.of(salonService.getSalonById(createServiceDto.getSalonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Salon not found"));


        Optional<Service> existingService = serviceRepository.findByNameAndSalonId(createServiceDto.getName(), createServiceDto.getSalonId());
        if (existingService.isPresent()) {
            throw new IllegalArgumentException("Service already exists in this salon.");
        }

        Service newService = new Service(
                createServiceDto.getName(),
                createServiceDto.getDescription(),
                createServiceDto.getPrice(),
                createServiceDto.getDuration(),
                salon,
                createServiceDto.getNo_of_employees()
        );

        return serviceRepository.save(newService);
    }


    public Service updateService(UUID serviceId, UpdateServiceDto updateServiceDto) throws ResourceNotFoundException {
        Service existingService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        existingService.setName(updateServiceDto.getName());
        existingService.setDescription(updateServiceDto.getDescription());
        existingService.setPrice(updateServiceDto.getPrice());
        existingService.setDuration(updateServiceDto.getDuration());
        existingService.setNo_of_employees(updateServiceDto.getNo_of_employees());

        return serviceRepository.save(existingService);
    }


    public List<Service> getAllServices(UUID salonId) {
        return serviceRepository.findAll().stream()
                .filter(service -> service.getSalon().getId().equals(salonId))
                .toList();
    }


    public Service findServiceById(UUID serviceId) throws ResourceNotFoundException {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }


    public void deleteService(UUID serviceId) throws ResourceNotFoundException {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        serviceRepository.delete(service);
    }
    public List<Service> getServiceByIds(List<UUID> serviceIds) {
        return serviceRepository.findAllById(serviceIds); // Or implement your logic here
    }
}
