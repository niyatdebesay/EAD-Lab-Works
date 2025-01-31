package com.example.Security.Salon.User.Model.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {

    private String firstName;
    private String lastName;
    @NotEmpty(message = "Username cant be empty")
    @Length(min = 3, max = 16)
    private String username;
    @Email(message = "Email must have a correct format")
    private String email;
    @Length(min = 8, max = 20,  message = "password must have a length between 8 and 20")
    private String password;
    private String phoneNumber;
}

