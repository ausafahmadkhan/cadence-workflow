package com.example.cadence.Service;

import com.example.cadence.Configuration.TaskLimiter;
import com.example.cadence.Enum.WorkFlowQueue;
import com.example.cadence.WorkFlows.UserWorkFlow;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private WorkflowClient workflowClient;

    @Autowired
    private TaskLimiter taskLimiter;

    @Autowired
    private AwsSqsService sqsClient;


    @Override
    public String enrollStudent(String userId)
    {
        String response = "";
        try {
            if (taskLimiter.isAllowed())
            {
                taskLimiter.acquire();
                response = enrollStudentTask(userId);
                taskLimiter.release();
            }
            else
            {
                //add the task to queue
                System.out.println("Sending to sqs");
                sqsClient.sendMessage(userId, WorkFlowQueue.UserWorkFLowQueue);
            }
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
        return response;
    }

    private String enrollStudentTask(String userId)
    {
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
