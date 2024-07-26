package com.requestapproval.requestapproval;

import com.requestapproval.requestapproval.Model.User.UsersEntity;
import com.requestapproval.requestapproval.Model.User.UsersRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class RequestApprovalApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RequestApprovalApplication.class, args);
        System.out.println("Hello world");

        UsersRepo usersRepo =  context.getBean(UsersRepo.class);
        UsersEntity userEntity1 = new UsersEntity();
        userEntity1.setUsr_id("uid_2");
        userEntity1.setName("Nikhil ahuja");
        userEntity1.setPhone("9138211120");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        try{
            Date dob = dateFormat.parse("26/9/1998");
            userEntity1.setDOB(dob);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        userEntity1.setEmail("ahujanikhil401@gmail.com");
        userEntity1.setStatus("A");
        UsersEntity userEntity2 = usersRepo.save(userEntity1);
        System.out.println(userEntity2);
    }
}
