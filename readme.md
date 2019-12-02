## SpringBoot使用RabbitMQ
> 该项目主要讲解了如何在Springboot项目中使用RabbitMQ
## 项目结构需知
* **1、yml文件，dev2为手动签收模式的配置文件，Java类中带有``Confirm``的都为手动签收模式**
* **2、除带``confirm``外的其他JAVA类都为自动签收**
## 启动前准备
**本地需要有RabbitMQ消息队列，且端口为``5672``**