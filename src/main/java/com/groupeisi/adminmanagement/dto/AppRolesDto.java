package com.groupeisi.adminmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class AppRolesDto {
    private int id;
    @NotNull
    private String name;
}
