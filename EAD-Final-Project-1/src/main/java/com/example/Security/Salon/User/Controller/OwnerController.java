package com.example.Security.Salon.User.Controller;

import com.example.Security.Salon.Exception.AlreadyExistsException;
import com.example.Security.Salon.Role.Model.Role;
import com.example.Security.Salon.Role.Service.RoleService;
import com.example.Security.Salon.User.Model.Dto.AddUserDto;
import com.example.Security.Salon.User.Model.Dto.UserResponseDto;
import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.baseUrl}/Salon_Admin")
public class OwnerController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid AddUserDto addUserDto) {
        try {
            Role role = roleService.findRoleByName("SALON_ADMIN");
            User user = userService.createUser(addUserDto, role);
            UserResponseDto response = new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(),
                    user.getUsername(), user.getPhoneNumber(), user.getEmail());
            System.out.println("Sending");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AlreadyExistsException e) {
            System.err.println("User already exists: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
