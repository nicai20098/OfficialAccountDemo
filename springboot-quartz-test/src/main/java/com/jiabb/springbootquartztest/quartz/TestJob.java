package com.jiabb.springbootquartztest.quartz;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * date 2020-10-21
 * ps:需要实现Job接口 然后重写execute方法中的实现
 *      其内容就是具体需要执行的业务内容 比如发送邮件短信等功能
 */
@DisallowConcurrentExecution
//Job中的任务有可能并发执行，
// 例如任务的执行时间过长，而每次触发的时间间隔太短，则会导致任务会被并发执行。
// 如果是并发执行，就需要一个数据库锁去避免一个数据被多次处理。
@Slf4j
public class TestJob implements Job {

    //创建任务(JobDetail)或者创建触发器(Trigger)的那里传入参数，然后在这里通过JobExecutionContext来获取参数进行处理
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //todo 体需要执行的业务内容
        log.info("TestJob --> JobDetail 获取获取name-> {}",jobExecutionContext.getJobDetail().getJobDataMap().get("name"));
        log.error("TestJob --> Trigger 获取参数orderNo-> {}",jobExecutionContext.getTrigger().getJobDataMap().get("orderNo"));
        log.info("定时任务执行，当前时间：{}" , DateUtil.formatDateTime(new Date()));
    }
}
