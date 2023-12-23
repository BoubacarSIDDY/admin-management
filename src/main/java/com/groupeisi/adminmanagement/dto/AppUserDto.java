package com.groupeisi.adminmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppUserDto {
    private int id;
    @NotNull(message = "the name is required")
    private String nom;
    @NotNull(message = "the lastname is required")
    private String prenom;
    @NotNull(message = "the email is required")
    private String email;
    @NotNull(message = "the password is required")
    private String password;
    @NotNull(message = "the state is required")
    private int etat;
}
