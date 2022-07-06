# 系统架构

采用srping cloud alibaba + gateway + nacos 架构，各服务代码、数据库解耦。内外网gateway独立部署。  

| 服务名          | 说明                        |
| --------------- | --------------------------- |
| account-service | 登录、注册服务（外网）             |
| profile-service | 用户信息服务（外网）                |
| auth-service    | 提供用户登录态创建和鉴权（内网，待完善） |
| gateway    | 普通用户统一入口网关（外网） |
| admin-service    | 管理员登录和批量删除用户（内网） |__
| admin-gateway    | 管理员统一入口网关 （内网）|

## Demo实现功能介绍

实现普通用户的登录、注册、修改用户信息、发邮件激活、注销用户，管理员登录和删除用户功能  
普通用户注销后可以通过注册再次激活，管理员删除用户后用户不能再激活。
1. 内外网登录鉴权和操作权限独立，禁止外网访问内网接口。
2. 微服务内网接口增加权限控制
3. 登录采用SSO私有token加密方式。用户登录态鉴权目前采用算法方式（一般应用于Auth服务failover的降级）
4. 对外服务使用Hstrix对用户信息服务熔断、降级
5. 数据库用户密码，手机号等敏感信息加密
6. 防止XSS攻击和SQL注入

## Demo Todo List

因为时间关系一下功能暂未实现  
1. 服务接口API应该使用HTTPS方式访问  
2. 前端注册登录敏感字段传输前应加密
3. Nacos 使用本地单机模式，未使用mysql持久化。项目中Mysql、gateway、权限控制的token等配置应该在Nacos中配置
4. 未使用Redis 或其他分布式Cache。并发能力受限于Mysql性能。
5. Auth-service没有实现微服务方式强校验（对密码更改后token过期等功能支持更好）
6. CRSF攻击仅仅实现了Cookie httpOnly ，未增加Referrer校验、临时token双重验证

## 关于性能测试

本机一般使用jmeter或则ab进行测试。本项目再本机运行受限于本机内存大小，使用默认JVM默认配置，也没有使用Cache固没有涉及性能测试部分。  

## docker 部署（docker compose）
cd to root path of this profile  

请先执行mvn clean package后根据环境执行一下命令
mac m1 环境:    
docker compose -f docker-compose-mac-m1.yml  up -d  

other 环境:  
docker compose -f docker-compose.yml up -d   or   docker compose up -d

## 关于使用
注册使用真实邮箱，如果收到不到邮件可以在account-service中看到日志中"The email" log的code（如有+号，code需要URLEncode）
注册密码是8到20位的数字、英文字母和特殊字符
登录后没有相关Cookie，请检查前后的域名是否一致。建议使用127.0.0.1 + 端口访问 


## 本机docker自测环境

### Mysql

version:5.7.38    
other version : https://hub.docker.com/r/mysql/mysql-server/tags/    

install：  
docker pull mysql/mysql-server:5.7.38-1.2.8-server  
docker run --name mysql  --network internal_net --network external_net -p 3306:3306 -d -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_ROOT_HOST=% mysql/mysql-server:5.7.38-1.2.8-server  

### Create database and tables
SQL脚本在 mysql/sql/mysql_init.sql

### Nacos

### mac m1 芯片

docker pull zhusaidong/nacos-server-m1:2.0.3  
docker run --name nacos --network internal_net --network external_net -e MODE=standalone -e JVM_XMS=512m -e JVM_XMX=512m -e JVM_XMN=256m -p 8848:8848 -d zhusaidong/nacos-server-m1:2.0.3  

管理后台
admin: http://127.0.0.1:8848/    
user/password: nacos/nacos   




