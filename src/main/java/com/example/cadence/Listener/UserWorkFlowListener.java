package com.example.cadence.Listener;

import com.example.cadence.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class UserWorkFlowListener
{
    @Autowired
    private UserService userService;

    @JmsListener(destination = "UserWorkFLowQueue")
    public void listenToUserWorkflow(String message)
    {
        System.out.println("Listening to UserWorkFLowQueue...");
        System.out.println("Message received : "+ message);
        userService.enrollStudent(message);
    }
}
