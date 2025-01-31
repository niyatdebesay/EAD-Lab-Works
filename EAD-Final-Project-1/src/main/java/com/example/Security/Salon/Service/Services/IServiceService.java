package com.example.Security.Salon.Service.Services;

import com.example.Security.Salon.Service.Model.Dto.CreateServiceDto;
import com.example.Security.Salon.Service.Model.Dto.UpdateServiceDto;
import com.example.Security.Salon.Service.Model.Service;

import java.util.List;
import java.util.UUID;

public interface IServiceService {

    Service addService(CreateServiceDto createServiceDto) throws Exception;
    Service updateService(UUID serviceId, UpdateServiceDto updateServiceDto) throws Exception;
    Service findServiceById(UUID serviceId) throws Exception;
    List<Service> getAllServices(UUID salonID);
    void deleteService(UUID serviceId) throws Exception;
    List<Service> getServiceByIds(List<UUID> serviceIds);
}
