package com.example.Security.Salon.User.Service;
import com.example.Security.Salon.Exception.AlreadyExistsException;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Role.Model.Role;
import com.example.Security.Salon.User.Model.Dto.AddUserDto;
import com.example.Security.Salon.User.Model.Dto.EditUserDto;
import com.example.Security.Salon.User.Model.User;

import java.util.UUID;

public interface IUserService  {
    User createUser(AddUserDto addUserDto, Role role) throws AlreadyExistsException;
    User editUser(EditUserDto editUserDto, UUID userId) throws Exception;
    User findUserById(UUID userId) throws Exception;
    User findUserByEmail(String email) throws Exception ;
    User findUserByUsername(String username) throws Exception;
    String deleteUser(UUID id) throws Exception;

    User findUserWithRole(UUID adminId,String role) throws ResourceNotFoundException;
}
