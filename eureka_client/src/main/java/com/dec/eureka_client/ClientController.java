package com.dec.eureka_client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @RequestMapping("/sayhello")
    @ResponseBody
    public String sayHello() {
        return "sayhello";
    }

}
