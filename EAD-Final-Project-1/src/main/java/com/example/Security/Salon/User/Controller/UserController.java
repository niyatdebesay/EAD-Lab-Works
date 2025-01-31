package com.example.Security.Salon.User.Controller;



import com.example.Security.Salon.Exception.AlreadyExistsException;
import com.example.Security.Salon.Role.Model.Role;
import com.example.Security.Salon.Role.Service.RoleService;
import com.example.Security.Salon.User.Model.Dto.*;
import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.baseUrl}/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // Create User
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid AddUserDto addUserDto) {
        try {
            Role role = roleService.findRoleByName("USER");
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) throws Exception {
        String response = userService.login(loginDto);
                return ResponseEntity.ok(response);


    }

    // Edit User
    @PutMapping("/{id}")
    public ResponseEntity<UserEditResponseDto> editUser(@PathVariable UUID id, @RequestBody EditUserDto editUserDto) {
        try {
            User user = userService.editUser(editUserDto, id);
            UserEditResponseDto response = new UserEditResponseDto(user.getId(), "User details updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Find User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable UUID id) {
        try {
            User user = userService.findUserById(id);
            UserResponseDto response = new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(),
                    user.getUsername(), user.getPhoneNumber(), user.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Find User by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> findUserByEmail(@PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email);
            UserResponseDto response = new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(),
                    user.getUsername(), user.getPhoneNumber(), user.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Find User by Username
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> findUserByUsername(@PathVariable String username) {
        try {
            User user = userService.findUserByUsername(username);
            UserResponseDto response = new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(),
                    user.getUsername(), user.getPhoneNumber(), user.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        try {
            String message = userService.deleteUser(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}

