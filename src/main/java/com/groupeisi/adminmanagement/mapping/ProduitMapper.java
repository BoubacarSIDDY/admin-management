package com.groupeisi.adminmanagement.mapping;

import com.groupeisi.adminmanagement.dto.ProduitDto;
import com.groupeisi.adminmanagement.entities.ProduitEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProduitMapper {
    ProduitDto toProduitDto(ProduitEntity produitEntity); // Transforme un objet ProduitEntity en un objet ProduitDto
    ProduitEntity fromProduitEntity(ProduitDto produitDto); // Transforme un objet ProduitDto en un objet ProduitEntity
}
