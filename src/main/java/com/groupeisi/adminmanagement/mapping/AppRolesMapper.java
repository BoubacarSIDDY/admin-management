package com.groupeisi.adminmanagement.mapping;

import com.groupeisi.adminmanagement.dto.AppRolesDto;
import com.groupeisi.adminmanagement.entities.AppRolesEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AppRolesMapper {
    AppRolesDto toAppRolesDto(AppRolesEntity appRolesEntity); // Transforme un objet AppRolesEntity en un objet AppRolesDto.
    AppRolesEntity fromAppRolesEntity(AppRolesDto appRolesDto); // Transforme un objet AppRolesDto en un objet AppRolesEntity.
}
