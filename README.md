### springboot设计策略
#### 开箱即用

####约定大于配置

### spring boot属性加载顺序
1、命令行中传入的参数

2、SPRING_APPLICATION_JSON中的属性。SPRING_APPLICATION_JSON是以JSON格式配置再系统环境变量中的内容(双引号要加\转义)

3、java:comp/env中的JNDI属性

4、JAVA的属性，可以通过System.getProperties() 获得的内容

5、操作系统的环境变量（注意：通过环境变量进行配置时，因为不支持"."，所以需要将其更换为"_"；代码可见：org.springframework.core.env.SystemEnvironmentPropertySource.resolvePropertyName）

6、通过random.*配置的随机属性

7、位于当前应用jar包之外，针对不同{profile}环境的配置文件内容，例如application-{profile}.properties或YAML定义的配置文件

8、位于当前应用jar包之内，针对不同{profile}环境的配置文件内容，例如application-{profile}.properties或YAML定义的配置文件。

9、位于当前应用jar包之外的application.properties或YAML配置内容

10、位于当前应用jar包之内的application.properties或YAML配置内容

11、在@Configuration注解修改的类中，通过@PropertySource注解定义的属性

12、应用默认属性，使用SpringApplication.setDefaultProperties定义的内容

源码：org.springframework.boot.context.config.ConfigFileApplicationListener

### logback-spring.xml配置slf4j日志
能通过<springProperty标签来引用application.properties\
logback.xml的加载顺序早于springboot的application.yml (或application.properties) 


