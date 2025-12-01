### springboot设计策略
#### 开箱即用
@EnableAutoConfiguration注解实现自动获取候选配置并加载
- 从配置文件spring-autoconfigure-metadata.properties 获得自动装配过滤规则元数据，框架会根据里面的规则逐一对候选类进行计算看是否需要被自动装配进容器，并不是全部加载
- 通过spring-boot的SPI机制，从spring.factories获得所有EnableAutoConfiguration的自动装配配置主类信息，并去掉一些重复的
- 排除需要排除的类，具体操作是通过@SpringBootApplication 注解中的 exclude、excludeName、环境属性中的 spring.autoconfigure.exclude配置
- 根据  spring-autoconfigure-metadata.properties 中配置的规则过虑掉一部分引导类 
##### spring-autoconfigure-metadata.properties 
内容格式： (自动配置的类全名.条件Condition=值)



####约定大于配置
##### 配置文件目录：
- file:./config/
- file:./
- classpath:/config/
- classpath:/
##### 默认配置文件
- application.yml
- application.yaml
- application.properties

### spring boot属性加载顺序
1、命令行中传入的参数

2、SPRING_APPLICATION_JSON中的属性。SPRING_APPLICATION_JSON是以JSON格式配置再系统环境变量中的内容(双引号要加\转义)

3、java:comp/env中的JNDI属性

4、JAVA的属性，可以通过System.getProperties() 获得的内容

5、操作系统的环境变量（注意：通过环境变量进行配置时，可以将"."替换为"_"；代码可见：org.springframework.core.env.SystemEnvironmentPropertySource.resolvePropertyName）

6、通过random.*配置的随机属性

7、位于当前应用jar包之外，针对不同{profile}环境的配置文件内容，例如application-{profile}.properties或YAML定义的配置文件

8、位于当前应用jar包之内，针对不同{profile}环境的配置文件内容，例如application-{profile}.properties或YAML定义的配置文件。

9、位于当前应用jar包之外的application.properties或YAML配置内容

10、位于当前应用jar包之内的application.properties或YAML配置内容

11、在@Configuration注解修改的类中，通过@PropertySource注解定义的属性

12、应用默认属性，使用SpringApplication.setDefaultProperties定义的内容
#### application配置文件读取pom属性
Spring Boot已经将maven-resources-plugins默认的 ${}方式改为了@@方式，如@project.version@，若要配置成${project.version}的方式，可以在pom中设置属性<resource.delimiter>${*}</resource.delimiter>

##### 源码
org.springframework.boot.context.config.ConfigFileApplicationListener



### logback-spring.xml配置slf4j日志
能通过<springProperty标签来引用application.properties\
logback.xml的加载顺序早于springboot的application.yml (或application.properties) 


### TODO
- ApplicationContextInitializer

```xml
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>3.0.5</version>
</dependency>
```
