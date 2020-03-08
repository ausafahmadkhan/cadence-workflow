package com.example.cadence.Service;

import com.example.cadence.Configuration.TaskLimitSemaphore;
import com.example.cadence.WorkFlows.UserWorkFlow;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class UserServiceImpl implements UserService
{
    private static String DOMAIN = "local";


    @Autowired
    private TaskLimitSemaphore taskLimitSemaphore;

    @Override
    public String enrollStudent(String userId) throws Exception {
        Future<String> future = taskLimitSemaphore.execute(() -> enrollStudentUnprotected(userId));
        while (!future.isDone())
            Thread.sleep(200);
        return future.get();
    }

    private String enrollStudentUnprotected(String userId)
    {
        WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);
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
