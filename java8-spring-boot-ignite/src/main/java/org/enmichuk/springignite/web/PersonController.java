package org.enmichuk.springignite.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @RequestMapping("/person")
    public String person() {
        return "Hello1";
    }
}
