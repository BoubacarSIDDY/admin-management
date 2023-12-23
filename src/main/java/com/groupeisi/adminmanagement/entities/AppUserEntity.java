package com.groupeisi.adminmanagement.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String nom;
    @Column(nullable = false, length = 200)
    private String prenom;
    @Column(nullable = false, length = 200)
    private String email;
    @Column(nullable = false, length = 255)
    private String password;
    private int etat;
    @ManyToMany() // rélation plusieurs à plusieurs
    private List<AppRolesEntity> appRoleEntities;
    @OneToMany(mappedBy = "appUserDto") // marque le côté propriétaire de la rélation et que mappedBy appUserDto => colonne étrangère de AppUserDto dans ProduitDto
    private List<ProduitEntity> produitEntities;
}
