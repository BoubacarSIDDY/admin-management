package com.groupeisi.adminmanagement.services;

import com.groupeisi.adminmanagement.dto.AppRolesDto;
import com.groupeisi.adminmanagement.exceptions.EntityNotFoundException;
import com.groupeisi.adminmanagement.exceptions.RequestException;
import com.groupeisi.adminmanagement.mapping.AppRolesMapper;
import com.groupeisi.adminmanagement.repositories.IAppRoleRepository;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppRolesService {
    private IAppRoleRepository iAppRoleRepository;
    private AppRolesMapper appRolesMapper;
    MessageSource messageSource; // permet de lire le fichier messages.properties dans resources

    public AppRolesService(IAppRoleRepository iAppRoleRepository, AppRolesMapper appRolesMapper, MessageSource messageSource) {
        this.iAppRoleRepository = iAppRoleRepository;
        this.appRolesMapper = appRolesMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public List<AppRolesDto> getAppRoles() {
        return StreamSupport.stream(iAppRoleRepository.findAll().spliterator(), false)
                .map(appRolesMapper::toAppRolesDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AppRolesDto getAppRole(int id) {
        return appRolesMapper.toAppRolesDto(iAppRoleRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    @Transactional
    public AppRolesDto createAppRoles(AppRolesDto appRolesDto) {
        return appRolesMapper.toAppRolesDto(iAppRoleRepository.save(appRolesMapper.fromAppRolesEntity(appRolesDto)));
    }

    @Transactional
    public AppRolesDto updateAppRoles(int id, AppRolesDto appRolesDto) {
        return iAppRoleRepository.findById(id)
                .map(entity -> {
                    appRolesDto.setId(id);
                    return appRolesMapper.toAppRolesDto(
                            iAppRoleRepository.save(appRolesMapper.fromAppRolesEntity(appRolesDto)));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
                        Locale.getDefault())));
    }

    @Transactional
    public void deleteAppRoles(int id) {
        try {
            iAppRoleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("role.errordeletion", new Object[]{id},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}
