package com.requestapproval.requestapproval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RequestApprovalApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RequestApprovalApplication.class, args);

    }
}
