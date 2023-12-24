package com.groupeisi.adminmanagement.services;

import com.groupeisi.adminmanagement.dto.AppRolesDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppRolesServiceTest {

    @Autowired
    AppRolesService appRolesService;

    @Test
    void getAppRoles() {
        List <AppRolesDto> appRolesDto = appRolesService.getAppRoles();

        Assertions.assertNotNull(appRolesDto);
    }

    @Test
    void getAppRole() {
        AppRolesDto appRolesDto = appRolesService.getAppRole(1);
        Assertions.assertNotNull(appRolesDto);
    }

    @Test
    void createAppRoles() {
        AppRolesDto appRolesDto = new AppRolesDto();
        appRolesDto.setName("ROLE_COMPTABLE1");
        AppRolesDto appRolesDtoSave = appRolesService.createAppRoles(appRolesDto);
        Assertions.assertNotNull(appRolesDtoSave);
    }

    @Test
    void updateAppRoles() {
        AppRolesDto appRolesDto = new AppRolesDto();
        appRolesDto.setName("ROLE_ADMIN");
        AppRolesDto appRolesDtoSave = appRolesService.updateAppRoles(1, appRolesDto);
        Assertions.assertEquals( "ROLE_ADMIN", appRolesDtoSave.getName());
    }

    @Test
    void deleteAppRoles() {
        appRolesService.deleteAppRoles(2);
        Assertions.assertTrue(true);
    }
}