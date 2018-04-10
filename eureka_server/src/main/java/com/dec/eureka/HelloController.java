package com.dec.eureka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hellotttt")
    public String hello(){
        return "hello world!";
    }
}
