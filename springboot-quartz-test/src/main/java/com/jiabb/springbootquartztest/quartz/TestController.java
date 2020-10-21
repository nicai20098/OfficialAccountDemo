package com.jiabb.springbootquartztest.quartz;

import cn.hutool.core.date.DateUtil;
import com.jiabb.springbootquartztest.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@RestController
@Slf4j
public class TestController {
    @Autowired
    private Scheduler scheduler;

    /**
     * 任务新建启动
     * @param orderNo 任务流水号 这里用订单好表示
     * @return ResultDTO 返回执行的结果
     * @throws Exception
     */
    @PostMapping("/Quartz")
    @ResponseBody
    public ResultDTO quartz(@RequestParam("orderNo") String orderNo) throws Exception {
        Date start = new Date(System.currentTimeMillis() + 7 * 1000);//当前时间7秒之后
        /**通过JobBuilder.newJob()方法获取到当前Job的具体实现(以下均为链式调用)
         * 这里是固定Job创建，所以代码写死XXX.class
         * 如果是动态的，根据不同的类来创建Job，则 ((Job)Class.forName("com.zy.job.TestJob").newInstance()).getClass()
         * 即是 JobBuilder.newJob(((Job)Class.forName("com.zy.job.TestJob").newInstance()).getClass())
         * */
        JobDetail jobDetail = JobBuilder.newJob(TestJobBean.class)
                /**给当前JobDetail添加参数，K V形式*/
                .usingJobData("name", "zy")
                /**给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getJobDetail().getJobDataMap().get("age")获取值*/
                /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
                .withIdentity(orderNo)
                .build();//执行


        Trigger trigger = TriggerBuilder.newTrigger()
                /**给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getTrigger().getJobDataMap().get("orderNo")获取值*/
                .usingJobData("orderNo", orderNo)
                /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
                .withIdentity(orderNo)
                /**立即生效*/
                //.startNow()
                /**开始执行时间*/
                .startAt(start)
                /**结束执行时间*/
                //.endAt(start)
                /**添加执行规则，SimpleTrigger、CronTrigger的区别主要就在这里*/
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                /**每隔1s执行一次*/
                                .withIntervalInSeconds(3)
                                /**一直执行，*/
                                .repeatForever()
                )
                //.withSchedule(CronScheduleBuilder.cronSchedule("* 30 10 ? * 1/5 2018"))
                .build();//执行




        /**添加定时任务*/
        scheduler.scheduleJob(jobDetail, trigger);
        if (!scheduler.isShutdown()) {
            /**启动*/
            scheduler.start();
        }
        log.info("TestController-->定时任务启动成功 [{}]" ,DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        return ResultDTO.ofSuccess();
    }

    /**
     * 任务停止或者暂停
     * @param orderNo 任务流水号 这里用订单好表示
     * @return ResultDTO
     * @throws SchedulerException
     */
    @PostMapping("/shutdown")
    @ResponseBody
    public ResultDTO shutdown(@RequestParam("orderNo") String orderNo) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(orderNo));//暂停Trigger
        log.info("TestController-->任务 [{}] 暂停",orderNo);
        return ResultDTO.ofSuccess();
    }

    /**
     * 任务恢复
     * @param orderNo 任务流水号 这里用订单好表示
     * @return ResultDTO
     * @throws SchedulerException
     */
    @PostMapping("/resume")
    @ResponseBody
    public ResultDTO resume(@RequestParam("orderNo") String orderNo) throws SchedulerException {
        scheduler.resumeTrigger(TriggerKey.triggerKey(orderNo));//恢复Trigger
        log.info("TestController-->任务 [{}] 恢复",orderNo);
        return ResultDTO.ofSuccess();
    }

    /**
     * 任务删除
     * @param orderNo 任务流水号 这里用订单好表示
     * @return ResultDTO
     * @throws IOException
     * @throws SchedulerException
     */
    @PostMapping("/del")
    @ResponseBody
    public ResultDTO del(@RequestParam("orderNo") String orderNo) throws IOException, SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(orderNo));//暂停触发器
        scheduler.unscheduleJob(TriggerKey.triggerKey(orderNo));//移除触发器
        scheduler.deleteJob(JobKey.jobKey(orderNo));//删除Job
        log.info("TestController-->任务 [{}] 删除",orderNo);
        return ResultDTO.ofSuccess();
    }
}
