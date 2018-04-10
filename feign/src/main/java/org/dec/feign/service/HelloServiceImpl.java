package org.dec.feign.service;

import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String helloFeign(String msg) {
        return "hello feign!";
    }
}
