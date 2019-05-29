## 南京信息工程大学论文期刊索引项目

---  
> 代码写的很乱，因为赶时间所有有些部分没有细细规划，有些内容并不适合线上部署。


1. 项目介绍:
    - 没啥好介绍的, 都是基础。就是写起来比较浪费时间, 让我深刻认识到很多时候开发就是个体力活, 和搬砖没啥区别。
    - 额...相比传统分层多加了两层分别是manager和facade, 即用作了缓存, 也作用了其他
        * manager作为repository和service的中间层, 缓存了大量数据库中常用数据, 同时抽象出service层中公共代码, 使service更为精炼, 解耦更为方便, 同时也为日后更换坑的不行的jpa提供方便。
        * facade作为controller和service中间层, 借鉴了门面模式的思想, 没啥好说的。
        
2. 技能点
    - SpringBoot作为整体框架
    - 将默认集成的Tomcat替换成Undertow。
    - 使用aop做日志记录(当然不是很完全...), 接口访问限制, 关键操作记录, 以及上传文件处理完成之后删除文件。
    - 使用Spring Schedule作为定时任务, 定期清理缓存, 同时定期查看是否任然存在没有被删除的临时目录。
    - 使用redis作为缓存, 并且设置了两层缓存层。
    - 使用Jsoup和Servlet中filter实现了防Xss的过滤。
    - 使用Shiro和jwt实现了鉴权处理。
    - 使用Kaptcha生成验证码, 并可以通过修改配置文件实现二维码的自定义。
    - 使用Swagger生成了接口文档
 
3. 如何运行
    - 所需环境: jdk8, mysql, redis
    - 命令
    ```
    git clone https://github.com/lwolvej/paper-project.git
    sudo mvn clean install package -DskipTests
    java -jar target/paper-1.2.0.jar
    ```
    - 打开: http://localhost:6374/swagger-ui.html可以查看接口文档。
    
4. 感谢国家感谢党, 感谢南信大图书馆:)

5. Todo List
    - 接口使用GraphQL
    - 框架更换
    - 代码重构
    - 使用kotlin
