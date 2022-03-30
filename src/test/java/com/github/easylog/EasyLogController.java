package com.github.easylog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Hansel Ma
 * @date: 2022/3/27
 */
@RestController
public class EasyLogController {

    @EasyLog(operationType = "test")
    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
