## maven

### Tips

1. maven中若A项目引入了dependency.a ,B项目引入A项目作为依赖，则B项目不许与引入dependency.a即可使用

   常见使用： 新建一个module引入基础的包并存放常用类，其他项目引入该module

2. 新版本的springBoot不支持bootstrap文件，需要手动引入相关依赖才能正常使用

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-bootstrap</artifactId>
   </dependency>
   ```
3. spring gateway不支持spring boot web包

   需要移除相关依赖
4. 配置加载顺序
   
   - bootstrap - application - nacos共享 - nacos
   - yaml - properties
   - 后加载的配置会覆盖先加载的配置






### Problems

#### parent 中 relativePath作用

作用，代表从什么地方查找parent pom文件

- 不写，默认去寻找 ../pom.xml
- \<relativePath/> 从仓库中寻找 本地或者远程
- \<relativePath>../config/pom.xml\<relativePath/> 从指定位置去寻找

比如引入spring作为parent时 不加该标签爆红

