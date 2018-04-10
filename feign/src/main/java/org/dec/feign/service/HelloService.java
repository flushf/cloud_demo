package org.dec.feign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "customer",fallback = HelloServiceImpl.class)
public interface HelloService {
    @RequestMapping(value = "/service/test", method = RequestMethod.GET)
    @ResponseBody
    String helloFeign(@RequestParam("msg") String msg);
}
