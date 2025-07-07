package com.example.authms.business;

import com.example.authms.configuration.EndpointRoleConfig;
import com.example.authms.configuration.EndpointUtil;
import com.example.authms.configuration.JwtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCheckManager {

    private final JwtService jwtService;
    private final EndpointRoleConfig endpointRoleConfig;

    public boolean isAuthorized(String token, String requestEndpoint, String httpMethod) {
        List<String> userRoles = StringUtils.isNotBlank(token) ? jwtService.extractRoles(token) : List.of();

        List<String> requiredRoles = getRolesForEndpoint(requestEndpoint, httpMethod);

        if (requiredRoles == null) {
            return false;
        } else if (requiredRoles.isEmpty()) {
            return true;
        } else {
            return userRoles.stream().anyMatch(requiredRoles::contains);
        }
    }

    private List<String> getRolesForEndpoint(String requestEndpoint, String httpMethod) {
        return endpointRoleConfig.getMappings().stream()
                .filter(m -> EndpointUtil.isMatchWithBaseEndpoint(requestEndpoint, m.getEndpoint())
                             && m.getMethod().equalsIgnoreCase(httpMethod)
                )
                .map(EndpointRoleConfig.EndpointRoleMapping::getRoles)
                .findFirst()
                .orElse(null);
    }
}
