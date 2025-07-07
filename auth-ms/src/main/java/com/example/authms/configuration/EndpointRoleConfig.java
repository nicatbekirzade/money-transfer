package com.example.authms.configuration;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "endpoint-roles")
public class EndpointRoleConfig {

    private List<EndpointRoleMapping> mappings = new ArrayList<>();

    @Getter
    @Setter
    public static class EndpointRoleMapping {
        private String endpoint;
        private String method;
        private List<String> roles;
    }
}