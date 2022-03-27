package com.github.easylog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.easylog
 * @NAME: EasyLogController
 * @AUTHOR: Hansel Ma
 * @DATE: 2022/3/27
 * @PROJECT_NAME: easy-log
 */
@RestController
public class EasyLogController {

    @EasyLog(operateType = "test")
    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
