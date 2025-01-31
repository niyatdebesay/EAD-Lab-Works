package com.example.Security.Salon.Role.Service;

import com.example.Security.Salon.Role.Model.Role;
import com.example.Security.Salon.Role.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleByName(String name){
        return roleRepository.findByName(name);

    }
}
