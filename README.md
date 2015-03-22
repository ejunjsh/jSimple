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

### 6.dao编写
```java
@Bean
public class BlogDao {
    @Inject
    private JSimpleDataTemplate jSimpleDataTemplate;

    public void insert(Blog entity) {
        jSimpleDataTemplate.insert(entity);
    }


    public void update(Blog entity) {
        jSimpleDataTemplate.update(entity);
    }

    public void delete(Long id) {
        jSimpleDataTemplate.delete(id, Blog.class);
    }

    public Blog getById(Long id) {
        return jSimpleDataTemplate.getById(Blog.class, id);
    }
    
    //getter/setter
}
```
`@Bean`代表此类放进bean容器里面，`@Inject` 注入进去，可以传参数进去代表注入的是那个bean例如`@Inject("jSimpleDataTemplate")`,同时注意的是被注入对象必须有setter

### 7.service编写
```java
@Bean
public class BlogService implements IBlogService {
    @Inject
    private BlogDao blogDao;

    @Transactional
    public void insert(Blog entity) {
        blogDao.insert(entity);
    }

    @Transactional
    public void update(Blog entity) {
        blogDao.update(entity);
    }

    public void delete(Long id) {
        blogDao.delete(id, Blog.class);
    }

    public Blog getById(Long id) {
        return blogDao.getById(Blog.class, id);
    }
   
    //getter/setter
}
```
`@Transactional` 事务处理，里面可以放入多个dao操作。

### 8.Controller 编写
```java
@Bean
public class BlogController extends ControllerBase {
   @Inject
   private IBlogService blogService;
   
   @HttpPost("/api/blog")
   public ActionResult addBlog(Blog blog)  {
   
           blog.setCreatedDate(new Date());
           blog.setLastModifiedDate(new Date());
           blog.setUid(1);
           blog.setViewCount(0);
           blogService.insert(blog);
   
           return json(blog);
   }
   
   //setter/getter
}
```
controller中最重要的是用`@HttpPost` 标记的方法，方法参数是由前端post过来参数，支持封装到一个对象，map等。<br />
例如：url /api/blog/{id}/edit?page=1 <br />
相应的参数是(int page,int id)或者是(Map map)或者是包含page和id两个属性的实体。 <br />
目前有`@HttpPost` `@HttpGet` `@HttpPut` `@HttpDelete` 四种标记。 <br />

返回的`ActionResult` 支持多种，json，html，jsp，file，freemarker，velocity等，同时易于扩展。
例如，我要返回一个验证码，那我要怎么实现一个`ActionResult`,代码如下：
```java
public class ValidateCodeResult extends ActionResult {
    @Override
    public void ExecuteResult() {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        //验证码类，用来生成验证码的类库
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
        } catch (Exception e) {
            throw new JSimpleException(e);
        }
    }

}
```
在controller调用
```java
@HttpGet("/validateCode")
public ActionResult getValidateCode()
{
   return new ValidateCodeResult();
}
```
So easy!

### 9.异常处理
每个controller里面都有一个onException的方法，默认是返回打印错误信息的页面，直接覆盖onException可以实现自定义的异常
例如一个ajax请求，我不需要返回一个错误信息的页面而是一个json，那么代码可以这样写：
```java
 public class AjaxController extends ControllerBase {
 
     public void onException(Throwable e, HttpServletRequest request, HttpServletResponse response) {
         try {
             Map map=new HashMap();
             map.put("error",e);
             json(map).ExcuteResult();
         } catch (JSimpleException e1) {
             // TODO Auto-generated catch block
             e1.printStackTrace();
         }
     }
     
 }
```

### 10.拦截器编写
假如我要实现一个非登陆跳转的拦截器，代码如下：
```java
@Order(98)
public class NeedLoginInterceptor extends Interceptor {

    public Class<? extends Annotation> getAnnotation() {
        return Login.class;
    }

    @Override
    public ActionResult before(Class<?> cls, Method method, Object[] params) {
        if (BlogContext.getUser() == null) {
            return new RedirectResult("/user/login?path=" + request.getAttribute("encodeUrl"));
        }
        return null;
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, ActionResult result) {
    }

}
```
`@Order`注解表示，这个拦截器执行顺序，越大表示执行最前面。`getAnnotation()`表示标记了什么注解方法会被拦截。<br />
`before()` 和 `after()` 代表方法之前和之后执行，必须要注意的是`before()`返回非空表示直接返回，不往下执行了。

### 总结
这个框架还存在很多问题，我只能说，就当学习一下呗。

## PS
再次强调，拿了别人的代码改成自己的总是不好的，请原作者见谅。