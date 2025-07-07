package com.example.userms.business;

import com.example.userms.entity.Role;
import com.example.userms.exception.NotFoundException;
import com.example.userms.exception.constants.ErrorInfo;
import com.example.userms.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleManager {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new NotFoundException(ErrorInfo.ROLE_NOT_FOUND));
    }
}
