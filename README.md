# spider_project

### 配置文件
缺少1个配置文件application.yml; 根据自己实际情况配置数据库即可;
```
server:
  port: 30001
spring:
  datasource:
    url: jdbc:mysql://localhost/xxxx?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: root
    password: xxxx
    driver-class-name: com.mysql.jdbc.Driver
  application:
    name: spider_project
```<bash>
