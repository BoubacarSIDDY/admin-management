package com.groupeisi.adminmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class ProduitEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 150)
    private String name;
    private double qtyStock;
    @ManyToOne // chaque entité de produit est associé à un seul utilisateur
    private AppUserEntity appUserEntity;
}
