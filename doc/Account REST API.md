# Account REST API

## 1	环境变量

### 默认环境1
| 参数名 | 字段值 |
| ------ | ------ |
|baseUrl|http://localhost:8081|


## 2	Account REST API

##### 说明
> 



##### 联系方式
- **联系人：**thomas
- **邮箱：**thomas_lee007@hotmail.com


##### 文档版本
```
V1
```


## 3	内网-管理员操作，只供admin服务调用

## 3.1	删除多个用户

> DELETE  /v1/internal/account/multiple-delete
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|X-Agent-Type||||X-User-Token|||
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|uids||用户uid列表|
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
> 需要申请,只能通过内网访问




## 4	外网-用户登录、注册、更新、删除

## 4.1	邮箱激活

> GET  /v1/account/activate
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|code||邮件链接中code参数|
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
> 




## 4.2	用户自己删除

> DELETE  /v1/account/delete
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|A0001||用户登录后Cookie||X-User-ID||gateway 设置，调用方忽略|
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
> 需要登录态（登录后Cookie中A0001）




## 4.3	用户登录

> POST  /v1/account/login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| countryCode|string||false|中国86开头|
| email|string||false|邮箱|
| phone|string||false|手机号|
| pwd|string||false|密码|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|用户信息实体|
|⇥ icon|string||false|头像|
|⇥ nickname|string||false|昵称|
|⇥ uid|int32||false|用户id|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> 手机号登录countryCode和phone必填




## 4.4	注册

> POST  /v1/account/register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| countryCode|string||false|中国86开头|
| email|string||false|邮箱|
| phone|string||false|手机号|
| pwd|string||false|密码|
| regType|string||false|注册类型，e-邮箱注册,p-手机注册|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|用户信息实体|
|⇥ icon|string||false|头像|
|⇥ nickname|string||false|昵称|
|⇥ uid|int32||false|用户id|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> regType为必填项，手机号登录countryCode和phone必填。手机号省略了手机验证码功能，注册后直接激活




## 4.5	重发邮件

> PUT  /v1/account/send-email
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|email||邮件地址|
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
> 




## 4.6	更新

> PUT  /v1/account/update
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|A0001||用户登录后Cookie||X-User-ID||gateway 设置，调用方忽略|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| confirmPwd|string||false|确认新密码|
| icon|string||false|头像|
| newPwd|string||false|新密码|
| nickname|string||false|昵称|
| oldPwd|string||false|旧密码|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|用户信息实体|
|⇥ icon|string||false|头像|
|⇥ nickname|string||false|昵称|
|⇥ uid|int32||false|用户id|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> 需要登录态（登录后Cookie中A0001），昵称、头像、密码可以单独更新



