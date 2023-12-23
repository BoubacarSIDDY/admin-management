package com.groupeisi.adminmanagement.mapping;

import com.groupeisi.adminmanagement.dto.AppUserDto;
import com.groupeisi.adminmanagement.entities.AppUserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserMapper {
    AppUserDto toAppUserDto(AppUserEntity appUserEntity); // Transforme un objet AppUserEntity en un objet AppUserDto.
    AppUserEntity fromAppUserEntity(AppUserDto appUserDto); // Transforme un objet AppUserDto en un objet AppUserEntity.
}
