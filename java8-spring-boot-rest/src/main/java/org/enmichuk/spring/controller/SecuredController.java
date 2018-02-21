package org.enmichuk.spring.controller;

import org.enmichuk.spring.config.WebSecurityConfig;
import org.enmichuk.spring.service.SecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {
    @Autowired private SecuredService securedService;

    @RequestMapping("/user/data")
    public String userData() {
        return securedService.securedData() + " for " + WebSecurityConfig.USER;
    }

    @RequestMapping("/admin/data")
    public String adminData() {
        return securedService.securedData() + " for " + WebSecurityConfig.ADMIN;
    }
}
