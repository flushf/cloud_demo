spring cloud demo
eureka_server:注册中心(暂时未配置高可用) port;1111
eureka_client:服务提供者  port:8081
eureka_ribbon:服务消费者（客户端负载均衡） port:8090
feign:声明式服务调用  port:9090
zuul:网关(test)  port:5555
entity_basis:pojo

可以在ribbon,feign,eureka_client等启动类上加上@EnableZuulProxy启用网关服务，在bootstrap.yml或application.yml上配置路由