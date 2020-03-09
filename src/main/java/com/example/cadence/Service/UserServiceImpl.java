package com.example.cadence.Service;

import com.example.cadence.Configuration.TaskLimiter;
import com.example.cadence.Enum.SqsQueue;
import com.example.cadence.WorkFlows.UserWorkFlow;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    @Async
    public CompletableFuture<String> enrollStudent(String userId)
    {
        String response = "";
        if (taskLimiter.isAllowed())
        {
            taskLimiter.tryacquire();
            response = enrollStudentTask(userId);
            taskLimiter.release();
        }
        else
        {
            //add the task to queue
            System.out.println("Sending to sqs");
            sqsClient.sendMessage(userId, SqsQueue.UserWorkFLowQueue);
        }
        return CompletableFuture.completedFuture(response);
    }

    private String enrollStudentTask(String userId)
    {
        Date date = new Date();
        System.out.println("Triggering workflow at : " + date);

        WorkflowOptions options = new WorkflowOptions.Builder()
                .setWorkflowId("WorkFlow_" + UUID.randomUUID().toString() + " : " + date)
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
