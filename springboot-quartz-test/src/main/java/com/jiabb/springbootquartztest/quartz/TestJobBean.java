package com.jiabb.springbootquartztest.quartz;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

@Slf4j
public class TestJobBean extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //todo 体需要执行的业务内容
        log.info("TestJob --> JobDetail 获取获取name-> {}",jobExecutionContext.getJobDetail().getJobDataMap().get("name"));
        log.error("TestJob --> Trigger 获取参数orderNo-> {}",jobExecutionContext.getTrigger().getJobDataMap().get("orderNo"));
        log.info("定时任务执行，当前时间：{}" , DateUtil.formatDateTime(new Date()));
    }
}
