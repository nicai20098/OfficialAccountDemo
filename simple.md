## idea中优化配置：

### 	一、去掉IDEA中xml黄色背景色

​		1.Setting-->Editor-->Inspections的配置页，去掉SQL中No data sources configured(没有配置数据源)选项和SQL dialect detection(SQL方言检测)选项，点击OK

​		2.Setting-->Editor-->Color Scheme-->General的配置页面，Injected language fragment，去掉Background选项，点击OK

### 二、Linux日常

​     1.修改用户所属组

​		`chgrd -R 目标组名称 修改用的默认路径`

​	 2.查看linux用户列表

​		`cat /etc/passwd  可以在这个文件直接修改用户的默认路径`

​     3.查看当前用户 whoami

​     4.查看系统内核 uname -r

​     5.查看系统版本 cat /etc/os-release

​     4.查看进程 并按照由大到小排列 列出前十

​       `ps -e -o "%C : %p : %z : %a"|sort -k5 -nr|head -n 10`

​     5.修改用户所在组

​		usermod -g 用户组 用户名 (将用户名归入用户组中)

​     6.给用户增加目录的权限

​        chown -R 用户组:用户名 路径地址 给用户名添加指定目录的权限

### 三、网址集合

​		在线教程

​			1.菜鸟教程 https://www.runoob.com/
​			2.易百教程 https://www.yiibai.com/
​			3.码农教程 http://www.manongjc.com/
​			4.简单教程 https://www.twle.cn/
​			5.Break易站 https://www.breakyizhan.com/
​			6.C语言中文网 http://c.biancheng.net/
​			7.http://jenkov.com http://tutorials.jenkov.com/
​			8.http://baeldung.com https://www.baeldung.com/

​		视频教程

​			1.B站 https://www.bilibili.com/
​			2.慕课网 https://www.imooc.com/
​			3.中国大学MOOC https://www.icourse163.org/
​			4.网易云课堂 https://study.163.com
​			5.实验楼 https://www.lanqiao.cn/courses/
​			6.我要自学网
​			7.大学生自学网 http://v.dxsbb.com/jisuanji/
​			8.极客学院 https://www.jikexueyuan.com/

​		电子书

​			1.图灵社区 https://www.ituring.com.cn/
​			2.博文视点 http://www.broadview.com.cn/
​			3.书栈网 https://www.bookstack.cn/
​			4.计算机书籍控 http://bestcbooks.com/
​			5.it熊猫 https://itpanda.net/book/	

​		官网

​			1.Java
​			2.Spring https://spring.io/
​			3.MySQL https://www.mysql.com/
​			4.MyBatis https://mybatis.org/mybatis-3/zh/index.html
​			5.Vue.js https://cn.vuejs.org/
​			6.Linux https://www.linux.org/
​			7.Git https://git-scm.com/
​			8.Dubbo http://dubbo.apache.org/zh-cn/
​			9.Redis https://redis.io/

​		国内博客社区

​			1.CSDN https://blog.csdn.net/
​			2.博客园 https://www.cnblogs.com/
​			3.简书 https://www.jianshu.com/
​			4.思否 https://segmentfault.com/
​			5.开源中国 https://www.oschina.net
​			6.51CTO https://www.51cto.com/
​			7.V2EX https://www.v2ex.com
​			8.腾讯云社区 https://cloud.tencent.com/developer
​			9.阿里云社区 https://yq.aliyun.com
​			10.开发者头条 https://toutiao.io/
​			11.GitChat https://gitbook.cn/
​			12.知乎 https://www.zhihu.com/

​		国外技术博客社区

​			1.Stack Overflow https://stackoverflow.com/
​			2.http://dev.io http://dev.io
​			3.DZone https://dzone.com/
​			4.Bytes https://bytes.com/
​			5.Google Developers https://developers.google.com/

​		小微型博客

​			1.美团技术团队 https://tech.meituan.com/
​			2.阮一峰的网络日志 http://www.ruanyifeng.com/blog/
​			3.Web前端导航 http://www.alloyteam.com/nav/
​			4.廖雪峰的官方网站 https://www.liaoxuefeng.com/
​			5.酷壳 https://coolshell.cn/
​			6.人工智能社区 https://www.captainbed.net/blog-neo/

​		开源社区

​			1.GitHub https://github.com/
​			2.码云 https://gitee.com/

​		面试刷题

​			1.LeetCode https://leetcode-cn.com/
​			2.LintCode https://www.lintcode.com/
​			3.牛客网 https://www.nowcoder.com/

### 四、idea插件

​	1.lombok 代码简化神器
​    2.Rainbow Bracket 括号彩色展示
​    3.Translation 翻译插件
​    4.Key promoter X 快捷键提示工具
​    5.CodeGlance 代码地图
​    6.HighlightBracketPair 括号范围

### 五、docker

```shell
 docker run [可选参数] image
 #参数说明
 --name="Name"		容器名称
 -d 							以后台方式运行
 -it 							使用交互方式运行 进入容器查看内容
 -p 							指定容器端口  -p  8080:8080 前边为映射端口
 -P								随机指定端口
 
 docker ps [命令]
 		列出当前正在运行的镜像
    -a   列出曾经运行的镜像
    -n=? 列曾经运行的镜像 只列出?个 例如 docker ps -a -n=1 列出一个
    -q   只显示id  
 
 容器退出
    exit 退出容器并停止
    快捷键 ctrl + p + q 容器不停止退出
   
 容器删除
 		docker rm id	 只能删除非运行状态的容器
 		docker rm -f $(docker ps -ap)    删除所有容器
 		docker rm -a -q|xargs docker rm   删除所有容器
 		
 查看日志
    docker logs -f -t 容器id
  
 查看容器中的进程信息
    docker top 容器id 
    
 查看容器元数据
    docker inspect 容器id
    
 进入当前正在运行的容器
 #通常容器都是使用后台方式运行的，需要进入容器修改配置
 docker exec -it 容器id bashShell(/bin/bash)  进入容器后开启一个新的终端
 docker attach 容器id   											进入容器正在执行的终端
    
 从容器中拷贝文件到主机上
 docker cp 容器id:容器内路径 主机目标路径
```

```shell
命令小结:
attach						#当前shell下attach 连接指定运行镜像
build							#通过Dockerfile定制镜像
commit						#提交当前容器为新的镜像
cp								#从容器中拷贝指定文件或者目录到宿主机中
create						#创建一个新的容 同run 但不启动容器
diff							#查看docker容器变化
events						#从docker服务获取容器实时事件
exec 							#在已存在的容器上运行
export 						#导出容器的内容流作为一个tar归档文件 对应import命令
history 					#展示一个容器形成历史
images						#列出系统当前镜像
import 						#从tar包中的内容创建一个新的文件系统映射 对应export
info 							#显示系统相关信息
inspect 					#查看容器详细信息
kill 							#kill指定docker容器
load 							#从一个tar包中加载一个镜像 对应save
login 						#注册或者登陆一个docker源服务器
logout 						#从当前docker registry 退出
logs							#输出当前容器日志信息
port 							#查看映射端口对应的容器内部源端口
pause 						#暂停容器
ps 								#列出容器列表
pull 							#从docker镜像源服务器拉去指定镜像或则库镜像
push 							#推送指定镜像或者库镜像至docker源服务器
restart 					#重启运行的容器
rm 								#移除一个或者多个容器
rmi 							#移除一个或者多个镜像[无容器使用镜像才可删除，否则需删除相关容器才可继续删除或者 -f强制删除]
run								#创建一个新的容器并运行一个命令
save 							#保存一个镜像为一个tar包 对应load
search 						#在docker hub中搜索镜像
start							#启动容器
stop							#停止容器
tag								#给源中镜像打标签
unpause						#取消暂停容器
version						#查看docker版本
wait							#截取容器停止时的退出状态值

#如何确定是具名挂载还是匿名挂载 还是指定路径挂载
-v 容器内路径 							 #匿名挂载
-v 卷名:容器内路径					  #具名挂载
-v /宿主机路径:容器内路径 		#指定路径挂载
#拓展：
-v 容器内路径:ro/rw 改变读写权限
ro readonly     只读
rw readwrite    读写
```



