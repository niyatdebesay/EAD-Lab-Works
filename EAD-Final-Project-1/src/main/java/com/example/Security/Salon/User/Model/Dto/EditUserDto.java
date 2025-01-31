package com.example.Security.Salon.User.Model.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
@AllArgsConstructor
public class EditUserDto {

    private String firstName;
    private String lastName;
    @Length(min = 3, max = 16)
    private String username;
    @Email(message = "Email must have a correct format")
    private String email;
    private String phoneNumber;
}
