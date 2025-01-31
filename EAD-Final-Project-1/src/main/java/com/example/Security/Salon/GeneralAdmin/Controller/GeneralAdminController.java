package com.example.Security.Salon.GeneralAdmin.Controller;


import com.example.Security.Salon.GeneralAdmin.Service.GeneralAdminService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Salon.Model.Authorized;
import com.example.Security.Salon.Salon.Model.Dto.CreateSalonDto;
import com.example.Security.Salon.Salon.Model.Dto.SalonResponseDto;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.baseUrl}/General_Admin")
public class GeneralAdminController {


    private final GeneralAdminService generalAdminService;
    private final SalonService salonService;
    @Autowired
    public GeneralAdminController(GeneralAdminService generalAdminService, SalonService salonService) {
        this.generalAdminService = generalAdminService;
        this.salonService = salonService;
    }



    @PreAuthorize("hasRole('GENERAL_ADMIN')")
    @GetMapping("/Unauthorized")
    public ResponseEntity<List<CreateSalonDto>> getUnAuthorizedSalons() {
        try {
            List<Salon> salons = generalAdminService.findAllSalonsWithAuthorization(Authorized.UNAUTHORIZED);

            List<CreateSalonDto> createSalonsDto = salons.stream()
                    .map(salon -> new CreateSalonDto(
                            salon.getName(),
                            salon.getDescription(),
                            salon.getAddress(),
                            salon.getPhoneNumber(),
                            salon.getOpeningHours(),
                            salon.getImage(),
                            salon.getUser().getId()

                    ))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(createSalonsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('GENERAL_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Salon>> getAllSalons(){
        try{
            List<Salon> salons = generalAdminService.listAllSalons();
            return new ResponseEntity<>(salons, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GENERAL_ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/Authorized")
    public ResponseEntity<List<SalonResponseDto>> getAuthorizedSalons() {
        try {
            List<Salon> salons = generalAdminService.findAllSalonsWithAuthorization(Authorized.AUTHORIZED);
            List<SalonResponseDto> responseDto = salons.stream().map(
                    salon ->
                            new SalonResponseDto(
                                    salon.getName(),
                                    salon.getDescription(),
                                    salon.getAddress(),
                                    salon.getPhoneNumber(),
                                    salon.getOpeningHours(),
                                    salon.getImage(),
                                    salon.getAuthorized(),
                                    salon.getGeneralAdmin().getId(),
                                    salon.getAuthorizedAt()

                            )
            ).collect(Collectors.toList());
            return new ResponseEntity<>(responseDto,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GENERAL_ADMIN')")
    @PutMapping("/{salonId}/salon/{adminId}/Admin")
    public ResponseEntity<SalonResponseDto> authorizeASalon(@PathVariable UUID salonId, @PathVariable UUID adminId) {

        try {
            Salon salon = generalAdminService.authorizeASalon(salonId, adminId);
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

        } catch (ResourceNotFoundException | IllegalAccessException | IOException | MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @PreAuthorize("hasRole('GENERAL_ADMIN')")
    @PutMapping("/{id}/salon")
    public Salon updateSalon(@PathVariable UUID id, @RequestBody Salon salon) {
        return salonService.updateSalon(id, salon);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}/salon")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        try {
            salonService.deleteSalon(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
