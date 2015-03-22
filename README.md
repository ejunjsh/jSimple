# jSimple
## 简介
### 1.源代码基于smart-framework开发，并在基础上进行深度改造，或者说是按我的意思改造。
### 2.同时也借鉴了jFinal的mvc中的多视图处理。
### 3.源码模块结构如下：
```
jSimple-parent(jSimple父级模块，配置基础依赖)
 L aop--利用cglib实现的aop，支持多拦截器（未排序）
 L bean--一个bean容器和一个扫描器
 L blog--angularjs开发的blog
 L cache--简单缓存实现，包括内存和memcached
 L config--整个jsimple配置常量接口
 L data--简单数据访问接口，基于dbutils开发，实现简单ORM，还有延迟加载
 L exception--统一jsimple异常类
 L hessian--hessian使用例子
 L ioc--依赖注入，基于bean容器的注入
 L mvc--mvc框架，应该说还是好好用的
 L utils--基于apache公共java库的一个公共库
```
### PS. 拷贝别人代码，并改动成自己，望原作者见谅。

##入门
### 1.安装
clone代码之后，直接maven命令安装
```
mvn clean install
```
### 2.创建一个maven web工程
基本java目录结构如下：
```
com.sky.jSimple.blog
　　┗ controller/ 控制器
　　┗ entity/   实体
   ┗ dao/   数据访问
   ┗ service/   业务逻辑
   ┗ interceptor/   拦截器
```
### 3. 配置 Maven 依赖
```
<dependency>
   <groupId>com.sky.jSimple</groupId>
   <artifactId>jSimple-mvc</artifactId>
   <version>0.0.1-SNAPSHOT</version>
</dependency>
```
### 4. 编写配置

在 `resources` 目录下，创建一个名为 `jSimple.xml` 的文件，内容如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<jSimple>
    <mvc static-suffix=".jpg;.bmp;.jpeg;.png;.gif;.html;.css;.js;.htm;.ttf;.woff;.svg;.swf;.map"
         static-expire="3600"
            />
    <beans scan-package="${scan.package}">
        <bean id="dataSource"
              class="org.apache.commons.dbcp.BasicDataSource">
            <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
            <property name="url"
                      value="${jdbc.driver}"/>
            <property name="username" value="${jdbc.user}"/>
            <property name="password" value="${jdbc.pwd}"/>
            <property name="initialSize" value="10"/>
            <property name="maxIdle" value="20"/>
            <property name="minIdle" value="5"/>
            <property name="maxActive" value="50"/>
        </bean>
        <bean id="sessionFactory" class="com.sky.jSimple.data.SessionFactory">
            <property name="dataSource" ref="dataSource"/>
        </bean>
        <bean id="jSimpleDataTemplate" class="com.sky.jSimple.data.JSimpleDataTemplate">
            <property name="sessionFactory" ref="sessionFactory"/>
            <property name="dbType" value="mysql"/>
        </bean>
        <!--默认是memoryCacheManager,去掉注释用的是memcached-->
        <!--<bean id="memcachedCacheManager" class="com.sky.jSimple.cache.MemcachedCacheManager">-->
        <!--<property name="servers"  value="192.168.153.132:11111,192.168.153.133:11111" />-->
        <!--<property name="weights"  value="1,1" />-->
        <!--</bean>-->
        <!--<bean id="cacheProxy" class="com.sky.jSimple.cache.CacheProxy">-->
        <!--<property name="cacheManager"  ref="memcachedCacheManager" />-->
        <!--</bean>-->
    </beans>
</jSimple>
```
这里很像spring的做法，之所以用这个，主要是因为想实现一个类下面的多个bean实体的配置，例如多数据源,当然还有就是灵活配置

然后在`web.xml` 添加一个serlvet
```xml
 <servlet>  
        <servlet-name>jsimplemvc</servlet-name>  
        <servlet-class>com.sky.jSimple.mvc.DispatcherServlet</servlet-class>
        <init-param>
          <param-name>configPath</param-name>
          <param-value>/jSimple.xml</param-value>
        </init-param>
      <load-on-startup>1</load-on-startup>
    </servlet>  
    <servlet-mapping>  
        <servlet-name>jsimplemvc</servlet-name>  
        <url-pattern>/</url-pattern>  
    </servlet-mapping>  
```
### 5. 编写entity
```java
@Entity("blog")
public class Blog implements Serializable {

    @Id
    private long id;
    private String title;
    private String content;
    private Date createdDate;
    private Date lastModifiedDate;
    private long uid;
    private long viewCount;
    private String linkName;
    private long categoryId;
    private String tags;

    private int isRecommend;

    @GetEntity(condition = "id=?", values = "categoryId")
    private Category category;

    @GetEntity(condition = "id=?", values = "uid")
    private User user;

    @GetCount(condition = "blogId=?", values = "id", cls = Comment.class)
    private Long commentCount;
    
    //getter/setter

}
```
里面`@Entity`注解里面是表名，`@Id`注解是必须的,`@GetEntity`和`@GetCount`是延迟加载的对象注解。