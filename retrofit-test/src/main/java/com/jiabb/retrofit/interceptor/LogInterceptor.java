package com.jiabb.retrofit.interceptor;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiabinbin
 * @date 2020/12/23 11:13 下午
 * @classname LogInterceptor
 * @description LogInterceptor 日志添加trace_id
 */
public class LogInterceptor implements HandlerInterceptor {
    private final static String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
//        ThreadContext.put("traceId", traceId);
        MDC.put(TRACE_ID, traceId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        ThreadContext. remove(TRACE_ID);
        MDC.remove(TRACE_ID);
    }

}
