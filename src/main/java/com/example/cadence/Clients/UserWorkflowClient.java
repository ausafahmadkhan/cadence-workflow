package com.example.cadence.Clients;

import com.uber.cadence.client.WorkflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserWorkflowClient
{
    private static String DOMAIN = "local";

    @Bean
    public WorkflowClient getWorkflowClient()
    {
        System.out.println("Workflow client created....");
        return WorkflowClient.newInstance(DOMAIN);
    }
}
