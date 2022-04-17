# Mk-cloud 说明：

> 这是一个基于SpringCloud的云笔记系统，应用于毕业设计，仅供学习参考~

## 技术点

> 本系统使用的 JDK是 11版本，为了兼容性，请务必使用JDK11！

### 前端主要技术：

1. Vue3
2.  Element-plus+
3. TypeScript
4. Echarts

### 后端主要技术以及环境安装：

1. SpringBoot： 版本 2.3.5.RELEASE

   > 主要基础微服务框架

2. SpringCloud Alibaba： 2.2.6.RELEASE

   > nacos 注册中心（版本： 2.0.3）
   >
   > open-feign
   >
   > 详细看配置

   安装nacos单节点，建议使用Docker安装。

3. KafKa：

   > Kafka 使用Docker容器安装最新版本即可。

4. Elasticsearch

   > 安装版本：7.4.2
   >
   > 参考链接：https://blog.csdn.net/weixin_43505211/article/details/123514848

5. Seata

   > 分布式事务，详细请看 源代码里面  需要使用到的文件--》seata-server-1.4.2.zip
   >
   > 解压后在bin目录下启动seata-server.bat；
   >
   > ```shell
   > seata-server.bat -p 8091
   > ```

6. XxlJob 需要使用Postgres修改的版本

   > 源代码获取：https://gitee.com/lzy2018cn/xxljob-postgres.git
   >
   > 需要修改为自己的数据库以及email，然后启动： XxlJobAdminApplication
   >
   > 访问管理页面：http://localhost:12700/xxl-job-admin/

## 代码模块设计说明

### mk-cloud

> 最外层的依赖脚手架，配置了maven依赖包的各个版本

mk-base

> 基础服务：处理笔记相关的接口

mk-user

> 用户服务：处理用户相关的接口

mk-other

> 其他服务：处理其他业务的接口，比如文件上传，邮箱验证码的发送
