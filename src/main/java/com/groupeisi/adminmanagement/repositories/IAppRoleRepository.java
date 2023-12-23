package com.groupeisi.adminmanagement.repositories;

import com.groupeisi.adminmanagement.entities.AppRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppRoleRepository extends JpaRepository<AppRolesEntity, Integer> {
}
