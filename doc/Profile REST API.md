# Profile REST API

## 1	环境变量

### 默认环境1
| 参数名 | 字段值 |
| ------ | ------ |
|baseUrl|http://localhost:8082|


## 2	Profile REST API

##### 说明
> 



##### 联系方式
- **联系人：**thomas
- **邮箱：**thomas_lee007@hotmail.com


##### 文档版本
```
V1
```


## 3	外网-用户信息

## 3.1	获取用户信息

> GET  /v1/profile/get/{uid}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|uid||用户uid|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|用户信息实体|
|⇥ activated|boolean||false|是否激活|
|⇥ createTime|int32||false|创建时间|
|⇥ deleted|boolean||false|是否已删除|
|⇥ icon|string||false|头像|
|⇥ nickname|string||false|昵称|
|⇥ uid|int32||false|用户uid|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> 




## 4	内网-用户信息操作，只供account、admin服务调用

## 4.1	创建用户信息

> POST  /v1/internal/profile/create
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|X-Agent-Type||内网调用方标识，联系管理员获取||X-User-Token||内网调用方token，联系管理员获取|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| activated|boolean||false|是否激活|
| createTime|int32||false|创建时间|
| deleted|boolean||false|是否已删除|
| icon|string||false|头像|
| nickname|string||false|昵称|
| uid|int32||false|用户uid|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|用户信息实体|
|⇥ activated|boolean||false|是否激活|
|⇥ createTime|int32||false|创建时间|
|⇥ deleted|boolean||false|是否已删除|
|⇥ icon|string||false|头像|
|⇥ nickname|string||false|昵称|
|⇥ uid|int32||false|用户uid|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> 需要申请,只能通过内网访问




## 4.2	普通删除用户

> DELETE  /v1/internal/profile/delete
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|X-Agent-Type||内网调用方标识，联系管理员获取||X-User-Token||内网调用方token，联系管理员获取|
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|uid||用户uid|
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




## 4.3	获取用户信息

> GET  /v1/internal/profile/get/{uid}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|uid||用户uid|
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|X-Agent-Type||内网调用方标识，联系管理员获取||X-User-Token||内网调用方token，联系管理员获取|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|用户信息实体|
|⇥ activated|boolean||false|是否激活|
|⇥ createTime|int32||false|创建时间|
|⇥ deleted|boolean||false|是否已删除|
|⇥ icon|string||false|头像|
|⇥ nickname|string||false|昵称|
|⇥ uid|int32||false|用户uid|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> 需要申请,只能通过内网访问




## 4.4	删除多个用户

> DELETE  /v1/internal/profile/multiple-delete
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|X-Agent-Type||内网调用方标识，联系管理员获取||X-User-Token||内网调用方token，联系管理员获取|
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
> 管理员删除后，用户无法再自己激活。需要申请,只能通过内网访问




## 4.5	更新用户信息

> PUT  /v1/internal/profile/update
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|X-Agent-Type||内网调用方标识，联系管理员获取||X-User-Token||内网调用方token，联系管理员获取|
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| activated|boolean||false|是否激活|
| createTime|int32||false|创建时间|
| deleted|boolean||false|是否已删除|
| icon|string||false|头像|
| nickname|string||false|昵称|
| uid|int32||false|用户uid|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false|code码|
| data|object||false|用户信息实体|
|⇥ activated|boolean||false|是否激活|
|⇥ createTime|int32||false|创建时间|
|⇥ deleted|boolean||false|是否已删除|
|⇥ icon|string||false|头像|
|⇥ nickname|string||false|昵称|
|⇥ uid|int32||false|用户uid|
| message|string||false|提示信息|
| success|boolean||false|是否操作成功|
| type|string||false|成功、错误类型|

##### 接口描述
> 需要申请,只能通过内网访问



