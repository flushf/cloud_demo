package com.dec.eureka_client;

import com.dec.entity.User;
import com.dec.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/service")
public class TestController {
    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private DiscoveryClient client;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/hello")
    public String test(@RequestBody User user) {
        logger.info("phone:" + user.getPhone() + ",name:" + user.getUserName());
        List<ServiceInstance> customer = client.getInstances("customer");
        for (ServiceInstance serviceInstance : customer) {
            logger.info(serviceInstance.getHost() + serviceInstance.getPort());
        }
        return "hello world";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testFei(@RequestParam("msg") String msg) {
        return "test" + msg;
    }

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return userService.findAllUser(pageNum, pageSize);
    }

    @RequestMapping("/testRedis")
    @ResponseBody
    public String testRedis() {
        redisTemplate.opsForValue().set("key", "value");
        Object key = redisTemplate.opsForValue().get("key");
        return key.toString();
    }
}
