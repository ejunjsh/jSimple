一个借鉴smart,jfinal一些东西开发的框架。
目前模块包括
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