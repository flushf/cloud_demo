package com.dec.zuul;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestZuulController {

    @RequestMapping("sayhello")
    public String hello() {
        return "hello zuul";
    }
}
