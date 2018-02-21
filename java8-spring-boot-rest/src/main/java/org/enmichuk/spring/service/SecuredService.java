package org.enmichuk.spring.service;

import org.springframework.stereotype.Component;

@Component
public class SecuredService {
    public String securedData() {
        return "Secured data";
    }
}
