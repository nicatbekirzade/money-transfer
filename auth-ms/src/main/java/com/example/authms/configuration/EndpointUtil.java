package com.example.authms.configuration;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EndpointUtil {

    public boolean isMatchWithBaseEndpoint(String requestEndpoint, String basePattern) {
        if (basePattern.endsWith("**")) {
            String basePath = basePattern.substring(0, basePattern.length() - 2);
            return requestEndpoint.startsWith(basePath);
        }
        // Matches numbers or UUIDs anywhere in endpoint
        String regex = basePattern.replaceAll("\\{[^/]+}", "(d+|[a-fA-F0-9-]+)");
        regex = "^" + regex + "$";

        return Pattern.compile(regex).matcher(requestEndpoint).matches();
    }
}
