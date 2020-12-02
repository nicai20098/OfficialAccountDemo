### retrofit-spring-boot-stater

**目的**:替代okhttp/httpClient

**现状**:在`SpringBoot`项目直接使用`okhttp`、`httpClient`或者`RestTemplate`发起`HTTP`请求，既繁琐又不方便统一管理。因此，在这里推荐一个适用于`SpringBoot`项目的轻量级HTTP客户端框架retrofit-spring-boot-starter，使用非常简单方便，同时又提供诸多功能增强。目前项目已经更新至`2.2.2`版本，并且会持续进行迭代优化。

**前言**:Retrofit是适用于Android和Java且类型安全的HTTP客户端，其最大的特性的是支持通过接口的方式发起HTTP请求。而spring-boot是使用最广泛的Java开发框架，但是Retrofit官方没有支持与spring-boot框架快速整合，因此我们开发了retrofit-spring-boot-starter。

**功能特性**:
	自定义注入OkHttpClient/注解式拦截器/连接池管理/日志打印/请求重试
	错误解码器/全局拦截器/熔断降级/微服务之间的HTTP调用/调用适配器/数据转换器

**使用说明**:
	1.引入依赖

```pom
<dependency>
  <groupId>com.github.lianjiatech</groupId>
  <artifactId>retrofit-spring-boot-starter</artifactId>
  <version>2.2.2</version>
</dependency>
```

  2.定义接口
	接口必须使用@RetrofitClient注解标记~ http相关注解可参考官方文档:retrofit官方文档

```java
@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface HttpApi {

  @GET("person")
  Result<Person> getPerson(@Query("id") Long id);
}
```

