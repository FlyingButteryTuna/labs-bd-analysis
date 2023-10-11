package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class RestDemoController {
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String demoGet() {
        System.out.println("/data serv");
        return "Server uehehehehehe";
    }
}
