package com.example.Security.Salon.GeneralAdmin.Service;



import com.example.Security.Salon.Salon.Model.Authorized;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Salon.Service.Repositories.SalonRepository;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Service.UserService;
import com.example.Security.Salon.Utils.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;


@Service
public class GeneralAdminService implements IGeneralAdminService {

    private final SalonService salonService;
    private final EmailService emailService;
    private final UserService userService;
    private final SalonRepository salonRepository;

    @Autowired
    public GeneralAdminService( SalonService salonService, EmailService emailService, UserService userService, SalonRepository salonRepository) {

        this.salonService = salonService;
        this.emailService = emailService;
        this.userService = userService;
        this.salonRepository = salonRepository;
    }



    @Override
    public List<Salon> findAllSalonsWithAuthorization( Authorized authorized) {

        List<Salon> salon = salonService.findAllSalonsWithAuthorization(authorized  );
        System.out.println(salon);
        return salon;
    }

    @Override
    public Salon authorizeASalon(UUID salonId, UUID adminId) throws ResourceNotFoundException, IllegalAccessException, MessagingException, IOException {
        Salon salon = Optional.of(salonService.getSalonById(salonId)).orElseThrow(() -> new ResourceNotFoundException("Salon not found"));
        User generalAdmin = Optional.of(userService.findUserWithRole(adminId, "GENERAL_ADMIN")).orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
        System.out.println("Authorizing Salon: " + salonId + " by Admin: " + adminId);
        salon.setAuthorized(Authorized.AUTHORIZED);
        salon.setGeneralAdmin(generalAdmin);
        salon.setAuthorizedAt(new Date());
        salonRepository.save(salon);
        emailService.sendEmail(salon.getUser().getEmail(), salon.getUser().getFirstName(), salon.getName());
        return salon;
    }



    @Override
    public List<Salon> listAllSalons(){
        return salonService.listAllSalons();
    }


}
