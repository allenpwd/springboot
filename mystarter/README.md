##自定义场景启动器
### 功能
注入helloService，提供sayHello方法，前后缀可在配置文件中指定

一般专门写一个自动配置模块，然后启动器依赖自动配置，使用时只需引入启动器
- 将需要启动加载的启动配置类配置在META-INF/spring.factories
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
pwd.allen.hellospringbootstarterautoconfigurer.HelloAutoConfiguration
```
