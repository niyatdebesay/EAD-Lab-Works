package com.example.Security.Salon.User.Model.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class LoginDto {
    @Email
    private String email;
    @NotEmpty
    @Length(min = 8)
    private String password;
}
