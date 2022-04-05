package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/demo")
@PropertySource("classpath:mysql.properties")
@Api(tags = "DemoController", description = "測試讀取不冋的資源文件")
public class DemoController {

    @Value("${env}")
    private String env;
    @Value("${msg}")
    private String msg;
    @Value("${mysql.hostname}")
    private String mysl_url;
    @Value("${mysql.port}")
    private String mysql_port;

    @ApiOperation(value = "獲取配置文件的變數")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Map<String, Object> getDemoKey() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("env", env);
            map.put("msg", msg);
            map.put("mysql_url", mysl_url);
            map.put("mysql_port", mysql_port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
