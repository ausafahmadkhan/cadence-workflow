package com.example.cadence.WorkFlows;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WorkFlowClient
{
    @Bean
    public UserWorkFlow getUserWorkFlow()
    {
        WorkflowClient workflowClient = WorkflowClient.newInstance("local");
        Date date = new Date();

        WorkflowOptions options = new WorkflowOptions.Builder()
                .setWorkflowId("WorkFlow : " + date)
                .build();

        return workflowClient.newWorkflowStub(UserWorkFlow.class, options);
    }
}
