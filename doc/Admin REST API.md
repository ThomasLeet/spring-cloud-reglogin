# Admin REST API
[toc]
## 1	环境变量

### 默认环境1
| 参数名 | 字段值 |
| ------ | ------ |
|baseUrl|http://localhost:8083|


## 2	Admin REST API

##### 说明
> 



##### 联系方式
- **联系人：**thomas
- **邮箱：**thomas_lee007@hotmail.com
- **网址：**//

##### 文档版本
```
V1
```


## 3	内网-管理员操作必须经过Gateway

## 3.1	管理员登录

> POST  /v1/admin/login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| name|string||false|用户名|
| pwd|string||false|密码|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|管理员信息实体|
|⇥ name|string||false|管理员名称|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> 




## 3.2	管理员删除

> DELETE  /v1/admin/multiple-delete
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|X-Admin-User-ID||管理员登录后通过gateway鉴权结果|
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|uids||要删除的用户列表|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|操作结果|
|⇥ success|boolean||false|是否成功|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> 管理员需登录态



