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

### 六、python

```python
import os
os.getcwd() 					#获取当前的工作目录，即当前python脚本工作的目录
os.chdir('test') 			#改变当前脚本工作目录 相对于shell脚本
os.rename('','')			#文件重命名
os.remove('')					#删除文件
os.rmdir('')					#删除空文件夹
os.removedirs('')			#删除空文件夹
os.mkdir('')					#创建一个文件夹
os.listdir('')				#列出指定目录的所有文件和文件夹
os.name								#nt --> windows posix --> Linux/Unix/MacOS
os.environ						#获取环境配置
os.environ.get('PATH')#获取指定环境配置
os.sep 								#路径分隔符
os.path.abspath(path) #获取path规范的绝对路径
os.path.exists(path)	#如果path存在 则返回True
os.path.isdir(path)		#如果path是一个存在的目录 返回True 否则返回False
os.path.isfile(path)	#如果path是一个存在的文件 返回True 否则返回False
os.path.splitext(path)#用来将指定路径进行分隔，可以获取文件和后缀名


import sys
sys.path							#模块的查找路径
sys.argv							#传递给python脚本的命令行参数
sys.exit(code)				#让程序以指定的退出码结束
sys.stdin							#标准输入 可以通过它来获取用户的输入
sys.stdout						#标准输出 可以通过修改它来改变默认输出
sys.stderr						#错误输出 可以通过修改它来改变错误输出

import math
math.fabs(-100)				#取绝对值
math.ceil(34.01)			#向上取整
math.factorial(5)			#计算阶乘
math.floor(34.98)			#向下取整
math.pi								# 3.1415
math.pow(2,10)				#2的10次方
math.sin(math.pi/6)		#正弦值 余弦cos 正切tan

import random
random.random()							#生成[0，1)的随机浮点数
random.uniform(20,30)				#生成[20，30]的随机浮点数
random.randint(10,30)				#生成[10，30]的随机整数
random.randrange(20,30)			#生成[20,30)的随机整数
random.choice('abcdefg')		#从列表里随机取出一个元素
random.sample('abcdefg',3)	#从列表里随机取出指定个数的元素

import datetime
datetime.date(2020,1,1)			#创建一个日期
datetime.time(18,23,45)			#创建一个时间
datetime.datetime.now()			#获取当前的日期时间
datetime.datetime.now() + datetime.timedelta(3) #计算三天以后的日期时间

import time
time.time()													#获取从1970-01-01 00:00:00 UTC 到现在时间的秒数
time,strftime("%Y-%m-%d %H:%M:%S")	#获取指定格式输出时间
time.asctime()											
time.ctime()
time.sleep(10)											#让线程睡10s

import calendar										  
calendar.setfirstweekday(calendar.SUNDAY) #设置每周起始日期码 周一到周日对应0～6
calendar.firstweekday()							#返回当前每周起始码 默认为0
calendar.calendar(2020)							#生成2020年日历
calendar.isleap(2020)								#判断是否是闰年
calendar.leapdays(1996,2020)				#获取时间内有几个闰年
calendar.month(2020,12)							#打印 指定月份的日历
```

七、礼金

```shell
村里：王全胜 王翔 李强 张骞 亚辉 赵志凯 黄群 赵鹏 卫振 韩康 尉润之 王绍军
高中：李星华 贾伟 张飞 刘江涛 赵昌 张荣荣 刘文娟 刘力君 李青青 郭磊 张辉 李罡 路小飞 武晋龙 杨志文 赵培良 高坤
大学：韩欣悦 常晓伟 赵春林 杜沐华 韩铁 赵雁荣 孙越飞 姚怡成 杨文宇 李艳鹏 刘帅 荆有亮
工作：李珍渊 王鹏莎 赵雅卉 卢绛梓
```



