### 加载nacos配置中心上的配置的方式
#### 方式一：硬编码
```java
@NacosPropertySources({@NacosPropertySource(dataId = "dubbo-config", groupId = "dubbo", autoRefreshed = true)})
```
#### 方式二：纯配置
```properties
#开启配置预加载功能
nacos.config.bootstrap.enable=true
# 主配置服务器地址
nacos.config.server-addr=127.0.0.1:8848
# 主配置 data-id
nacos.config.data-id=eco-mock
# 主配置 group-id
nacos.config.group=MYSELF
# 主配置 配置文件类型
nacos.config.type=properties
# 主配置 最大重试次数
nacos.config.max-retry=10
# 主配置 开启自动刷新
nacos.config.auto-refresh=true
# 主配置 重试时间
nacos.config.config-retry-time=2333
# 主配置 配置监听长轮询超时时间
nacos.config.config-long-poll-timeout=46000
# 主配置 开启注册监听器预加载配置服务（除非特殊业务需求，否则不推荐打开该参数）
nacos.config.enable-remote-sync-config=true

```

### 问题
#### 配置读取不到
可能是配置格式有问题，不如注释被不小心放开
可以打断点在com.alibaba.nacos.api.config.ConfigService.getConfig；看看获取内容，如果能获取到内容，但是最终没能加载到，可能就是配置内容格式问题


### TODO
nacos配置是否能配置得比其他类型得配置要优先
bootstrap的作用