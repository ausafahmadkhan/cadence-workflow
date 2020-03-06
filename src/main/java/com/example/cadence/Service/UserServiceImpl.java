package com.example.cadence.Service;

import com.example.cadence.WorkFlows.UserActivity;
import com.example.cadence.WorkFlows.UserWorkFlow;
import com.example.cadence.WorkFlows.UserWorkFlowImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService
{
    @Override
    public String enrollStudent(String userId)
    {
        WorkflowClient workflowClient = WorkflowClient.newInstance("local");
        Date date = new Date();

        WorkflowOptions options = new WorkflowOptions.Builder()
                .setWorkflowId("WorkFlow : " + date)
                .build();

        UserWorkFlow userWorkFlow = workflowClient.newWorkflowStub(UserWorkFlow.class, options);

        try {
            userWorkFlow.createEnrollment(userId);
        }
        catch (Exception e)
        {
            System.out.println("Error : " + e.toString());
            System.out.println("Workflow cause : " + e.getMessage() + "Cause : " + e.getCause());
        }

        return "created enrollment";
    }
}
