# 快速搭建SpringCloud脚手架

> 项目地址： https://gitee.com/GeorgeChan/spring-cloud-parent.git

## 项目概览 :
![springcloud项目概览.png](https://upload-images.jianshu.io/upload_images/6558271-fddf956b76701ef3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 模块分析 ：

> spring-cloud-parent : 父工程，管理各子模块，锁定springboot和springcloud版           
> 
>                                       本，添加maven插件。
> 
> common : 公共子模块，定义公共类和公用的jar包供其它模块使用。
> 
> eureka-server : 注册中心服务端（单节点），服务注册和发现。
> 
> producer1 : 注册中心客户端。
> 
> producer2 : 注册中心客户端，与producer1一样，为了演示Feign的负载均衡。
> 
> consumers : 注册中心客户端，使用Feign调用producer1和producer2的接口，当
> 
>                        发生调用异常时，通过Hystrix进行熔断和降级。
> 
> config-server : 配置中心服务端，同时作为一个eureka客户端注册到注册中心，配
> 
>                            置bus总线，从远程仓库读取配置文件，并进行广播分发，动态刷
> 
>                            新配置信息。
> 
> config-client ： 配置中心客户端，同时作为一个eureka客户端注册到注册中心，配
> 
>                            置bus总线，通过服务端实时获取刷新后的配置文件。事实上除了
> 
>                            eureka服务端，每个模块都应当是配置中心的客户端，配置文件统
> 
>                            一由配置中心进行管理。这里只用了这一个模块进行演示。
> 
> zuul : zuul网关，请求统一从网关进行过滤和分发。服务端限流、token鉴权都在这 
> 
>           里进行。
> 
> base : 这里没有作为微服务的一部分，只是springboot整合了mybatis-plus,添加了
> 
>             mybatis代码生成器。只作为一个演示。

## 核心解读 ：

### 搭建注册中心服务端

$示例模块 : eureka-server$

- 引入pom依赖
  
  ```xml
  <dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-eureka- server</artifactId>
  </dependency>
  ```

- 配置文件
  
  ```yml
  eureka:
   server:
   # eureka缓存，true开启缓存，false关闭，生产环境建议为true
   enable-self-preservation: true
   client:
   register-with-eureka: false # 是否注册中心注册自己 true为是，可以在注册中心列表找到自己
   service-url:
   # Eureka客户端与Eureka服务端进行交互的地址
   defaultZone: http://localhost:${server.port}/eureka/
   # 是否从Eureka中获取服务信息
   fetch-registry: false
   # 多个注册中心集群
   # defaultZone: http://localhost:8761/eureka/,http://localhost:8080/eureka/
  ```

- 启动类添加注解
  
  ```java
  @SpringBootApplication
  @EnableEurekaServer
  public class EurekaServerApplication {
      public static void main(String[] args) {
          SpringApplication.run(EurekaServerApplication.class, args);
      }
  }
  ```

-   启动项目
  
  访问地址 ：[http://localhost:8761/](http://localhost:8761/) 

### 搭建注册中心客户端

$示例模块 : producer1、producer2、consumers$

- 引入pom依赖
  
  ```xml
  <dependency>
   <groupId>org.springframework.cloud</groupId>

   <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>

  </dependency>
  ```
- 配置文件
  
  ```yml
  spring:

    application:

      name: eureka-client-producer # 实例名称，唯一标识
  eureka:
    client:
      service-url: # 指向注册中心

        defaultZone: http://localhost:8761/eureka/
  ```
  
  配置文件只是示例，完整配置见代码。
- 启动类添加注解

```
@SpringBootApplication
@EnableEurekaClient
public class Product1Application {
    public static void main(String[] args) {
        SpringApplication.run(Product1Application.class, args);
    }
}
```

启动项目

访问注册中心，服务实例列表中出现则注册成功。

### Feign远程调用与Hystrix熔断降级

$示例模块 : producer1、producer2、consumers$

- 引入pom依赖
  
  ```xml
  <!--Eureka-client-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>

  </dependency>
  <!--feign-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-openfeign</artifactId>

  </dependency>
  ```

- 配置文件
  
  ```yml
  eureka:
    client:
      service-url: # 注册中心地址
        defaultZone: http://localhost:8761/eureka/
  feign:
    hystrix:  #开启熔断
      enabled: true
  ```

- 启动类添加注解
  
  ```java
  @SpringBootApplication
  @EnableEurekaClient
  @EnableFeignClients
  public class ConsumerApplication {
      public static void main(String[] args) {
          SpringApplication.run(ConsumerApplication.class, args);
      }
  }
  ```

- 远程调用示例
  
  ```java
  /**
   * <p>
   *  使用Feign进行远程调用
   *  name ：调用的服务实例名称，如果是集群则进行轮询负载
   *  fallback ：进行降级操作的类
   * </p>
   */
  @FeignClient(name = "eureka-client-producer", fallback = ProducerRemoteFallback.class)
  @Service
  public interface ProducerRemote {
      /**
       * 调用 eureka-client-producer 服务中，url为/test1 的接口
       * 接口地址、方法名，参数与远程服务接口一致
       * @param name 参数
       * @return 返回结果
       */
      @GetMapping("/test1")
      String test1(@RequestParam(value = "name") String name);
  }
  ```

- 熔断降级示例

```java
@Component
public class ProducerRemoteFallback implements ProducerRemote {
    @Override
    public String test1(String name) {
        return "操作异常，进行降级。。。。";
    }
}
```

### 搭建配置中心服务端

$示例模块 : config-server$

- 引入pom文件
  
  ```xml
  <!--Eureka-client-->
  <dependency>

    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>

  </dependency>
  <!--config-server-->
  <dependency>

    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-config-server</artifactId>

  </dependency>
  <!--消息总线-->
  <dependency>

    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-bus-amqp</artifactId>

  </dependency>
  ```

- 配置文件
  
  ```yml
  server:
    port: 9000 # 端口
  spring:
    application:
      name: config-server # 实例名称
    cloud:
      config:
        server:
          git:
            uri: https://gitee.com/GeorgeChan/spring-cloud-parent.git # git仓库的地址
            search-paths: config-file/dev,config-file/pro,config-file/test  # git仓库地址下的相对地址，可以配置多个，用,分割。
            username: 码云用户名 #用户名

            password: 码云密码#密码

    # rabbitmq 配置
    rabbitmq:
      host: localhost
      port: 5672
      username: admin
      password: admin
  # 配置注册中心
  eureka:
    client:
      service-url: # 注册中心地址
        defaultZone: http://localhost:8761/eureka/
  
  #  springboot 2.x 默认只开启了info、health的访问，*代表开启所有访问
  management:
    endpoints:
      web:
        exposure:
          include: "*"
  ```

- 启动类添加注解
  
  ```java
  @SpringBootApplication
  @EnableEurekaClient
  @EnableConfigServer
  public class ConfigServerApplication {
      public static void main(String[] args) {
          SpringApplication.run(ConfigServerApplication.class, args);
      }
  }
  ```

- 刷新配置信息
  
  这里使用了bus总线，当远程配置文件改变，可以通过访问url
  
  **curl -X POST http://127.0.0.1:9001/actuator/bus-refresh**

       将所有关联的配置中心客户端都进行一次刷新。

        $注：127.0.0.1:9001是其中一个配置中心客户端的地址$

### 搭建配置中心客户端

$示例模块 : config-client$

- 引入pom依赖
  
  ```xml
  <!--配置中心客户端依赖-->
  <dependency>

    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-config</artifactId>

  </dependency>

  <!--动态刷新配置中心-->

  <dependency>

    <groupId>org.springframework.boot</groupId>

    <artifactId>spring-boot-starter-actuator</artifactId>

  </dependency>

  <!--消息总线-->

  <dependency>

    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-bus-amqp</artifactId>

  </dependency>

  <!--Eureka-client-->

  <dependency>

    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>

  </dependency>
  ```

- 配置文件
  
  ```yml
  # 客户端配置文件必须命名为bootstrap.yml(.yaml、properties)
  spring:
    application:
      name: config-client # 实例名称，唯一标识
    cloud:
      config:
        name: config-server # 配置文件的前缀
        profile: dev # 配置文件环境标识
        label: master # 分支
        discovery:
          enabled: true # 开启config服务发现支持
          service-id: config-server # 配置中心server端的name （service-id存在就把uri删掉）
  #   消息总现----开启消息跟踪
      bus:
        trace:
          enabled: true
  #  消息总线----消息队列配置
    rabbitmq:
      host: localhost
      port: 5672
      username: admin
      password: admin
  # 配置注册中心
  eureka:
    client:
      service-url: # 注册中心地址
        defaultZone: http://localhost:8761/eureka/
#  springboot 2.x 默认只开启了info、health的访问，*代表开启所有访问
  management:
    endpoints:
      web:
        exposure:
          include: "*"
  ```

- 添加核心注解

        在需要从配置中心获取配置文件内容的java类上添加注解   $@RefreshScope$

```java
/**
 * <p>
 *  @RefreshScope 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
 *                  手动刷新配置：当配置文件发生改动的时候，需要访问接口  curl -X POST http://localhost:9001/actuator/refresh
 *                  [
 *                      "config.client.version",
 *                      "test.env"
 *                  ]
 *                  表示刷新成功！
 *                  localhost:9001 客户端ip和端口
 *
 *  如果使用bus总线刷新配置，请求：curl -X POST http://127.0.0.1:9001/actuator/bus-refresh

 *  所有配置都会刷新
 * </p>
 */
@RefreshScope
@RestController
public class ConfigClientController {

    @Value("${test.env}")
    private String hello;

    @GetMapping("/name")
    public String test() {
        return this.hello;
    }
}
```

### 搭建Zuul网关

- 引入pom依赖
  
  ```xml
  <!--Eureka-client-->
  <dependency>

    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>

  </dependency>

  <!--zuul-->

  <dependency>

    <groupId>org.springframework.cloud</groupId>

    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>

  </dependency>
  ```

- 配置文件
  
  ```yml
  spring:
    application:
      name: zuul # 实例名称 唯一标识
    # springboot解决中文乱码问题
    http:
      encoding:
        charset: UTF-8
        enabled: true
        force: true
  eureka:
    client:
      service-url: # 注册中心地址
        defaultZone: http://localhost:8761/eureka/

  zuul:
    routes: #自定义路由映射 (路由不能重复，下面会把上面的覆盖)
      eureka-client-producer: /api/producer/**
      eureka-client-consumers: /api/consumer/**
    # 忽略的服务
    ignored-patterns: /*-producer/**
  #  敏感的头信息 默认Cookie是不能通过网关头信息传递的
  #  sensitive-headers:
  ```

- 启动类添加注解
  
  ```java
  @SpringBootApplication
  @EnableEurekaClient
  @EnableZuulProxy
  public class ZuulApplication {
      public static void main(String[] args) {
          SpringApplication.run(ZuulApplication.class, args);
      }
  }
  ```

- 自定义过滤器，模拟token鉴权

```java
@Component
public class MyFilter extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyFilter.class);
    /**
     * 过滤器类型，前置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序，越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 过滤器是否生效
     * RequestContext 请求的全局对象
     * @return boolean 是否过滤
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String cookie = request.getHeader("Cookie");
        String authorization = request.getHeader("Authorization");
        LOGGER.info("Cookie ====》 {}", cookie);
        LOGGER.info("Authorization ====》 {}", authorization);
        return true;
    }

    /**
     * 业务逻辑
     * 模拟token鉴权，如果Authorization为空，表示没有权限
     * @return 
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String cookie = request.getHeader("Cookie");
        String authorization = request.getHeader("Authorization");

        // 这里模拟权限检验，失败返回401
        if (StringUtils.isEmpty(authorization)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            requestContext.setResponseBody("权限不足！");
        }
        return null;
    }
}
```

## 其它

### 启动顺序

当项目包含注册中心、配置中心以及网关时，启动顺序为

1. 注册中心服务端

2. 配置中心服务端

3. 注册（配置）中心客户端

4. 网关

*注意 ：以上代码只是示例，并不完整，详细请见 ====> [码云仓库](https://gitee.com/GeorgeChan/spring-cloud-parent.git)*


