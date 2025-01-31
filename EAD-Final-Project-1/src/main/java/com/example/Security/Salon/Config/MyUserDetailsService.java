package com.example.Security.Salon.Config;

import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Model.UserPrincipal;
import com.example.Security.Salon.User.Service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MyUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

    if (user == null){
        throw new UsernameNotFoundException(email);
    }


        return new UserPrincipal(user);
    }

    public UserDetails loadUserById(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserPrincipal(user);
        }
        throw new UsernameNotFoundException("User  not found with id: " + userId);
    }
}
