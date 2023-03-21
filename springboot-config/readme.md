###约定大于配置
##### 默认配置文件目录
- file:./config/
- file:./
- classpath:/config/
- classpath:/
##### 默认配置文件名称
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



依赖包里的配置文件也会被识别

### 配置属性优先级从高到低
- program arguments（命令行参数）例如：--server.port=81
- VM options（这个应该等同于java环境变量吧）
- 操作系统环境变量
- properties
- yml
官方文档：https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/#boot-features-external-config