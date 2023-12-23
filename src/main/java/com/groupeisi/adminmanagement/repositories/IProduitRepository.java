package com.groupeisi.adminmanagement.repositories;

import com.groupeisi.adminmanagement.entities.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProduitRepository extends JpaRepository<ProduitEntity, Integer> {
}
