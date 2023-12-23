package com.groupeisi.adminmanagement.repositories;

import com.groupeisi.adminmanagement.entities.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppUserRepository extends JpaRepository<AppUserEntity, Integer> {
    AppUserEntity findByEmail(String email); // get user by email
}
