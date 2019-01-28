# jSimple-blog
## 安装
### 1.clone所有jSimple代码
### 2.执行`mvn clean install -Dmaven.test.skip=true`
### 3.执行jSimple-blog目录下的mysql脚本文件`jSimpleBlog.sql`
### 4.修改`pom.xml`文件的以下`jdbc.user`和`jdbc.pwd`属性，配置成你自己mysql账号
```xml
<properties>
                <scan.package>com.sky.jSimple</scan.package>
                <jdbc.user>root</jdbc.user>
                <jdbc.pwd>sjj!@#$%^</jdbc.pwd>
                <jdbc.driver>jdbc:mysql://localhost:3306/jSimpleBlog</jdbc.driver>
</properties>
```
### 5.在jSimple-blog目录下执行`mvn jetty:run`
### 6.链接/admin 开头的都是后台，前台为/


__20190128__

我把lib文件夹的脚本压缩成一个lib.zip,所以要用的话就要先解压咯