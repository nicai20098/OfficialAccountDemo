package com.jiabb.retrofit.controller;

import com.jiabb.retrofit.domain.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiabinbin
 * @date 2020/12/3 8:46 上午
 * @classname DistController
 * @description 提供调用的接口 模拟外部接口
 */
@RestController
public class DistController {

    @RequestMapping("/support1")
    public ResponseVo support1(){
        return ResponseVo.ofSusses();
    }
}
