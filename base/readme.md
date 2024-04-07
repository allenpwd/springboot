### knife4j 
- 访问地址：localhost/doc.html
- 官方文档：https://doc.xiaominfo.com/v2/documentation/selfdocument.html
knife4j提供的资源接口
- /swagger-resources：Springfox-Swagger提供的分组接口
- /v2/api-docs：Springfox-Swagger提供的分组实例详情接口
- /swagger-ui.html：Springfox-Swagger提供的文档访问地址
- /swagger-resources/configuration/ui：Springfox-Swagger提供
- /swagger-resources/configuration/security：Springfox-Swagger提供


### controller
打印controller的mapping映射日志，可以配置以下两个debug中的其中一个：
```yaml
logging:
  level:
    springfox.documentation.spring.web.plugins.DefaultRequestHandlerCombiner: debug
```
打印的日志如下：
```text
2024-04-07 22:59:49.984 DEBUG 27496 --- [           main] s.d.s.w.p.DefaultRequestHandlerCombiner  : Adding key: /my/forward, WebMvcRequestHandler{requestMapping={ [/my/forward]}, handlerMethod=pwd.allen.base.controller.MyController#forward(HttpServletRequest, HttpServletResponse), key=RequestHandlerKey{pathMappings=[/my/forward], supportedMethods=[], supportedMediaTypes=[], producibleMediaTypes=[]}}
2024-04-07 22:59:49.984 DEBUG 27496 --- [           main] s.d.s.w.p.DefaultRequestHandlerCombiner  : Adding key: /my/get/{path}, WebMvcRequestHandler{requestMapping={GET [/my/get/{path}]}, handlerMethod=pwd.allen.base.controller.MyController#get(String, Date, Integer, Map), key=RequestHandlerKey{pathMappings=[/my/get/{path}], supportedMethods=[GET], supportedMediaTypes=[], producibleMediaTypes=[]}}
2024-04-07 22:59:49.984 DEBUG 27496 --- [           main] s.d.s.w.p.DefaultRequestHandlerCombiner  : Adding key: /my/redirect, WebMvcRequestHandler{requestMapping={GET [/my/redirect]}, handlerMethod=pwd.allen.base.controller.MyController#redirect(HttpServletResponse), key=RequestHandlerKey{pathMappings=[/my/redirect], supportedMethods=[GET], supportedMediaTypes=[], producibleMediaTypes=[]}}
2024-04-07 22:59:49.984 DEBUG 27496 --- [           main] s.d.s.w.p.DefaultRequestHandlerCombiner  : Adding key: /my/upload, WebMvcRequestHandler{requestMapping={POST [/my/upload]}, handlerMethod=pwd.allen.base.controller.MyController#upload(MultipartFile), key=RequestHandlerKey{pathMappings=[/my/upload], supportedMethods=[POST], supportedMediaTypes=[], producibleMediaTypes=[]}}
```
或者
```yaml
logging:
  level:
    _org.springframework.web.servlet.HandlerMapping.Mappings: debug
```
打印的日志如下：
```text
2024-04-07 22:59:49.490 DEBUG 27496 --- [           main] _.s.web.servlet.HandlerMapping.Mappings  : 
	p.a.b.c.MyController:
	{GET [/my/get/{path}]}: get(String,Date,Integer,Map)
	{GET [/my/redirect]}: redirect(HttpServletResponse)
	{ [/my/forward]}: forward(HttpServletRequest,HttpServletResponse)
	{POST [/my/upload]}: upload(MultipartFile)
```