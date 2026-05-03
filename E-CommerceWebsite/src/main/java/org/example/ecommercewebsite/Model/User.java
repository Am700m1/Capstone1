package org.example.ecommercewebsite.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "ID must be filled!")
    private String id;

    @NotEmpty(message = "Username must be filled!")
    @Size(min = 6, message = "Username must consist of at least 6 characters!")
    private String username;

    @NotEmpty(message = "Password must be filled!")
    @Size(min = 7, message = "Password must consist of at least 7 characters!")
    @Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@#$%^&+=])$", message = "Password must have characters, digits, and special character!")
    private String password;

    @Email
    private String email;

    @NotEmpty(message = "Role must be filled!")
    @Pattern(regexp = "Admin|Customer")
    private String role;

    @NotNull(message = "Balance must be filled")
    @Positive(message = "Balance must be a positive number!")
    private Double balance;
}
