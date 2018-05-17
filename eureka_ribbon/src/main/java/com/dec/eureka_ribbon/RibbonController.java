package com.dec.eureka_ribbon;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dec.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RibbonController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam("phone") String phone) {
        User user = new User();
        user.setPassword("1567676376");
        user.setUserId(ThreadLocalRandom.current().nextInt());
        user.setUserName("zhangsan");
        user.setPassword("989898");
        user.setPhone(phone);
        String body = restTemplate.postForEntity("http://customer/service/hello", user, String.class).getBody();
        return body;
    }

    @RequestMapping("/addUser")
    public String addUser(String name, String phone) {

        User user = new User();
        user.setUserName(name);
        user.setPassword("huiahf");
        user.setPhone(phone);
        user.setUserId((int) (Math.random() * 10000));
        String body = restTemplate.postForEntity("http://customer/add", user, String.class).getBody();
        return body;
    }

    @RequestMapping("/page/{pageNum}/{pageSize}")
    public Object page(@PathVariable int pageNum, @PathVariable int pageSize) {
        Object o = restTemplate.getForEntity("http://customer/all/{1}/{2}", List.class, pageNum, pageSize).getBody();
        return o;
    }

    @RequestMapping("sayhello")
    public String sayHi() {
        return "say hi";
    }

}
