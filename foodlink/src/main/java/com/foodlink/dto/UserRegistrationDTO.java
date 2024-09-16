package com.foodlink.dto;

import com.foodlink.permissions.UserTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserRegistrationDTO {

    @NotBlank(message = "Cnpj is required")
    private String cnpj;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;
    private UserTypeEnum userTypeEnum;

}
