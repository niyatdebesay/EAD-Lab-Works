package com.example.Security.Salon.Appointment.Service;

import com.example.Security.Salon.Appointment.Model.Appointment;
import com.example.Security.Salon.Appointment.Model.Dto.CreateAppointmentDTO;
import com.example.Security.Salon.Appointment.Model.Status;
import com.example.Security.Salon.Appointment.Service.Repository.AppointmentRespository;
import com.example.Security.Salon.GeneralAdmin.Service.GeneralAdminService;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Salon.Model.Salon;
import com.example.Security.Salon.Salon.Service.Repositories.SalonService;
import com.example.Security.Salon.Service.Services.ServiceService;
import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AppointmentService implements IAppointmentService {

    @Autowired
    private AppointmentRespository appointmentRepository;


    @Autowired
    private SalonService salonService;
    private final UserService userService;
    private final ServiceService serviceService;


    @Autowired
    public AppointmentService (SalonService salonService, UserService userService, ServiceService serviceService) {
        this.salonService = salonService;
        this.userService = userService;
        this.serviceService = serviceService;
    }



    @Transactional
    @Override

    public Appointment addAppointment(CreateAppointmentDTO createAppointmentDTO) throws Exception {

        Optional<Appointment> existingAppointment = appointmentRepository.findBySalon_IdAndDateTime(
                createAppointmentDTO.getSalonId(),
                createAppointmentDTO.getDateTime()
        );

        if (existingAppointment.isPresent()) {
            throw new IllegalStateException("An appointment already exists at this time for this employee and salon.");
        }


        Salon salon = salonService.getSalonById(createAppointmentDTO.getSalonId());
        User user = userService.findUserById(createAppointmentDTO.getUserId());

        List<com.example.Security.Salon.Appointment.Model.AppointmentService> appointmentServices = createAppointmentDTO.getServiceTimes().stream()
                .map(serviceTime -> {
                    com.example.Security.Salon.Service.Model.Service service = null;
                    try {
                        service = Optional.ofNullable(serviceService.findServiceById(serviceTime.getServiceId())).orElseThrow(()->new ResourceNotFoundException("Appointment not found"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return new com.example.Security.Salon.Appointment.Model.AppointmentService(
                            service,
                            serviceTime.getStartTime(),
                            serviceTime.getEndTime()
                    );
                })
                .collect(Collectors.toList());


        Appointment appointment = new Appointment(
                createAppointmentDTO.getDateTime(),
                Status.valueOf(createAppointmentDTO.getStatus()),
                appointmentServices,
                salon,
                user
        );
        appointmentServices.forEach(service -> service.setAppointment(appointment));
        return appointmentRepository.save(appointment);
    }
    @Override
    public Appointment getAppointment(UUID id) throws ResourceNotFoundException {
        return appointmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Appointment Not Found"));
    }

    @Override
    public Appointment updateAppointment(UUID id, CreateAppointmentDTO createAppointmentDto) {
        return null;
    }



    @Override
    public void deleteAppointment(UUID id) throws ResourceNotFoundException {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Appointment Not Found"));
        appointmentRepository.delete(appointment);

    }



    @Override
    public List<Appointment> getAllAppointmentsForSalon(UUID salonId) throws Exception {
        List<Appointment> appointments = appointmentRepository.findBySalon_Id(salonId) ;
        if(appointments.isEmpty()){
            throw  new ResourceNotFoundException("Wrong Salon Id");
        }
        return appointments;
    }

    @Override
    public String addRatingToAppointment(UUID appointmentID, int rating) throws Exception {

        Appointment appointment = appointmentRepository.findById(appointmentID).orElseThrow(()-> new ResourceNotFoundException("Appointment not found"));
        if ( appointment.getStatus().toString().equals("COMPLETED") ){
            appointment.setRating(rating);

        }

        List<Appointment> appoint = appointmentRepository.findBySalon_IdAndStatus(appointment.getSalon().getId(), Status.COMPLETED);
        int sum = 0;
        sum =  appoint.stream().mapToInt(
                individualAppointment->
                        (int)individualAppointment.getRating())
                         .sum();

        float employeesRating = (!appoint.isEmpty())? (float) sum /appoint.size(): 0;
        appointment.getSalon().setRating(employeesRating);

        return "";



    }


}
