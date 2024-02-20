package com.groupeisi.adminmanagement.controllers;

import com.groupeisi.adminmanagement.dto.AppRolesDto;
import com.groupeisi.adminmanagement.services.AppRolesService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@NoArgsConstructor
public class AppRolesController {
    @Autowired
    AppRolesService appRolesService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<AppRolesDto> getRoles(){
        return appRolesService.getAppRoles();
    }

    @GetMapping("/{id}")
    public AppRolesDto getAppRole(@PathVariable("id") int id){
        return appRolesService.getAppRole(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppRolesDto createAppRole(@Valid @RequestBody AppRolesDto appRolesDto){
        return appRolesService.createAppRoles(appRolesDto);
    }

    @PutMapping("{id}")
    public AppRolesDto updateAppRole(@PathVariable("id") int id, AppRolesDto appRolesDto){
        return appRolesService.updateAppRoles(id, appRolesDto);
    }

    @DeleteMapping("{id}")
    public void deleteAppRole(@PathVariable("id") int id){
        appRolesService.deleteAppRoles(id);
    }

    @GetMapping("/sessions")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }
}
