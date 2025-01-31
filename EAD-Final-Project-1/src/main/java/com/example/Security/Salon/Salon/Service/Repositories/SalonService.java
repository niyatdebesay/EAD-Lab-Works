package com.example.Security.Salon.Salon.Service.Repositories;

import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Salon.Model.Authorized;
import com.example.Security.Salon.Salon.Model.Dto.CreateSalonDto;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalonService {


    private final SalonRepository salonRepository;
    private final UserService userService;

    @Autowired
    public SalonService(SalonRepository salonRepository, UserService userService) {
        this.salonRepository = salonRepository;
        this.userService = userService;
    }

    public Salon createSalon(CreateSalonDto createSalonDto) throws Exception {

        Optional<Salon> existingSalon = salonRepository.findByName(createSalonDto.getName());
        if (existingSalon.isPresent()) {
            throw new RuntimeException("Salon with the name " + createSalonDto.getName() + " already exists.");
        }
        User user = userService.findUserById(createSalonDto.getCreatedById());
        Salon salon = new Salon(
                createSalonDto.getName(),
                createSalonDto.getDescription(),
                createSalonDto.getAddress(),
                createSalonDto.getPhoneNumber(),
                createSalonDto.getOpeningHours(),
                createSalonDto.getImage(),
                Authorized.UNAUTHORIZED,
                user

        );

        return salonRepository.save(salon);
    }

    public Salon getSalonById(UUID id) throws ResourceNotFoundException, IllegalAccessException {

        Salon salon =  salonRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Salon not found"));


            return salon;

    }

    public Salon updateSalon(UUID id, Salon salonDetails) {
        Salon salon = salonRepository.findById(id).orElseThrow(() -> new RuntimeException("Salon not found"));
        if (salon.getAuthorized() == Authorized.UNAUTHORIZED){
            throw new IllegalStateException("Salon not authorized yet, please wait patiently until it is authorized");

        }
        salon.setName(salonDetails.getName());
        salon.setDescription(salonDetails.getDescription());
        salon.setAddress(salonDetails.getAddress());
        salon.setPhoneNumber(salonDetails.getPhoneNumber());
        salon.setOpeningHours(salonDetails.getOpeningHours());
        salon.setImage(salonDetails.getImage());

        return salonRepository.save(salon);
    }

    public void deleteSalon(UUID id) {
        Salon salon = salonRepository.findById(id).orElseThrow(() -> new RuntimeException("Salon not found"));
        if (salon.getAuthorized() == Authorized.UNAUTHORIZED){
            throw new IllegalStateException("Salon not authorized yet, please wait patiently until it is authorized");

        }
        salonRepository.delete(salon);
    }

    public List<Salon> findAllSalonsWithAuthorization(Authorized authorized){
        return salonRepository.findByAuthorized(authorized);

    }

    public List<Salon> listAllSalons(){
        return salonRepository.findAll();
    }
}
