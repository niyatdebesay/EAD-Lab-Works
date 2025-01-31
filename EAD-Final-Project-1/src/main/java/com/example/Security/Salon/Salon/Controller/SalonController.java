package com.example.Security.Salon.Salon.Controller;

import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Salon.Model.Dto.CreateSalonDto;
import com.example.Security.Salon.Salon.Model.Dto.SalonResponseDto;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.baseUrl}/salons")
public class SalonController {

    @Autowired
    private SalonService salonService;


    @PreAuthorize("hasRole('SALON_ADMIN')")
    @PostMapping
    public ResponseEntity<String> createSalon(@RequestBody CreateSalonDto createSalonDto) throws Exception {
        Salon salon =  salonService.createSalon(createSalonDto);
        return new ResponseEntity<>("Salon created Successfully.Our team  will notify you once it has been authorized", HttpStatus.OK);
    }

//    @Secured("OWNER")
//    @PostMapping
//    public Salon AddEmployee(@RequestBody Salon salon) {
//        return salonService.createSalon(salon);
//    }


    @PreAuthorize("hasRole('SALON_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SalonResponseDto> getSalonById(@PathVariable UUID id) throws ResourceNotFoundException {
        try{
            Salon salon = salonService.getSalonById(id);
            SalonResponseDto salonResponseDto = new SalonResponseDto(
                    salon.getName(),
                    salon.getDescription(),
                    salon.getAddress(),
                    salon.getPhoneNumber(),
                    salon.getOpeningHours(),
                    salon.getImage(),
                    salon.getAuthorized(),
                    salon.getGeneralAdmin().getId(),
                    salon.getAuthorizedAt()

            );
            return new ResponseEntity<>(salonResponseDto, HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


    @PreAuthorize("hasRole('SALON_ADMIN')")
    @PutMapping("/{id}")
    public Salon updateSalon(@PathVariable UUID id, @RequestBody Salon salon) {
        return salonService.updateSalon(id, salon);
    }


    @PreAuthorize("hasRole('SALON_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteSalon(@PathVariable UUID id) {
        salonService.deleteSalon(id);
    }
}

