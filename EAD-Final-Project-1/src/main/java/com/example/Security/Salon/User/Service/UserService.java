package com.example.Security.Salon.User.Service;

import com.example.Security.Salon.Exception.AlreadyExistsException;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Role.Model.Role;
import com.example.Security.Salon.Role.Repository.RoleRepository;
import com.example.Security.Salon.User.Model.Dto.AddUserDto;
import com.example.Security.Salon.User.Model.Dto.EditUserDto;
import com.example.Security.Salon.User.Model.Dto.LoginDto;
import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Model.UserPrincipal;
import com.example.Security.Salon.User.Service.Repository.UserRepository;
import com.example.Security.Salon.Utils.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService  implements IUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder , AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @Override
    @Transactional
    public User createUser(AddUserDto addUserDto, Role role) throws AlreadyExistsException{
        return (User) Optional.ofNullable(userRepository.findByUsername(addUserDto.getUsername()))
                .map((existingUser) -> {
                    throw new AlreadyExistsException("User already exists with the username: " + addUserDto.getUsername());
                })
                .orElseGet(() -> {
                    String password = bCryptPasswordEncoder.encode(addUserDto.getPassword());


                    System.out.println("Fetched Role ID: " + role.getId());
                    System.out.println("Inserting user with role_id: " + role.getId());
                    User user = new User(
                            addUserDto.getFirstName(),
                            addUserDto.getLastName(),
                            addUserDto.getUsername(),
                            addUserDto.getPhoneNumber(),
                            addUserDto.getEmail(),
                            password,
                            role




                    );
                    return  userRepository.save(user);
                });
    }

public String login(LoginDto loginDto)  {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        if (authentication.isAuthenticated()){
            UserPrincipal users = (UserPrincipal) authentication.getPrincipal();
            UUID userId = users.getId();
            String role = users.getRoleId();
            return jwtService.generateToken(userId, role);
    }
        return "boo";


}

    @Override
    public User editUser(EditUserDto editUserDto, UUID id) throws Exception {
        return Optional.of(findUserById(id)).map((exsistingUser)->{
            exsistingUser.setFirstName(editUserDto.getFirstName());
            exsistingUser.setLastName(editUserDto.getLastName());
            exsistingUser.setPhoneNumber(editUserDto.getPhoneNumber());
            exsistingUser.setEmail(editUserDto.getEmail());
            exsistingUser.setUsername(editUserDto.getUsername());

            userRepository.save(exsistingUser);
            return exsistingUser;

        }).orElseThrow(()->
                new ResourceNotFoundException("User not found"));
    }


    @Override
    public User findUserById(UUID id) throws Exception {
        return userRepository.findById(id).orElseThrow( () ->new ResourceNotFoundException("User Not Found!"));

    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() ->new Exception("User Not Found!"));
    }

    @Override
    public User findUserByUsername(String username) throws Exception {
        return Optional.ofNullable(userRepository.findByUsername(username)).orElseThrow(() ->new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public String deleteUser(UUID id)  throws Exception {
        return Optional.of(userRepository.findById(id)).map((existingUser)-> {
            userRepository.deleteById(id);
            return "User deleted";
        }).orElseThrow(()->new ResourceNotFoundException("User Not Found!"));

    }

    @Override
    public  User findUserWithRole(UUID id, String role) throws ResourceNotFoundException {
        Role roleData = roleRepository.findByName(role);
        User user = userRepository.findByIdAndRole(id, roleData);
        if (user == null){
            throw new ResourceNotFoundException("User with the specified id not found");

        }
        else{
            return user;
        }



    }
}

