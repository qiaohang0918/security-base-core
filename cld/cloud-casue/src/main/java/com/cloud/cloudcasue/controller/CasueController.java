package com.cloud.cloudcasue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/9 21:42
 * @Modified By:
 */
@RestController
@RequestMapping("/casue")
public class CasueController {


    @GetMapping("/hi/{name}")
    public String sayHi(@PathVariable(name = "name") String name, HttpServletRequest request) throws Exception {
        return "hi :" + name +" , header:"+request.getAttribute("header");
//        throw new Exception("my casue exception");
    }

}
