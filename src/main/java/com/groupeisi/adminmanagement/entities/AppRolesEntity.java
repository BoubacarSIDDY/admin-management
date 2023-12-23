package com.groupeisi.adminmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class AppRolesEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false, length = 100)
    private String name;
    @ManyToMany(mappedBy = "appRoleDtos")
    private List<AppUserEntity> appUserEntities;
}
