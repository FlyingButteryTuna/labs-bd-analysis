package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping(value="/gateway")
public class RestDemoController {
    @Autowired
    RestTemplate restTemplate;

    @Value("${endpoint.server}")
    private String msEndpoint;
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String getData() {
        System.out.println("Returning data from gateway");
        return "Gateway uehehehehehe";
    }
    @RequestMapping(value = "/server-data", method = RequestMethod.GET)
    public String getServerData() {
        System.out.println("Returning data from serer through gateway");
        try {
            System.out.println("Endpoint name : [" + msEndpoint + "]");
            return restTemplate.getForObject(new URI(Objects.requireNonNull(msEndpoint)), String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Exception occurred";
    }
}
