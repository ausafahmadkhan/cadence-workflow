package com.example.cadence.Service;

import com.example.cadence.WorkFlows.UserActivity;
import com.example.cadence.WorkFlows.UserActivityImpl;
import com.example.cadence.WorkFlows.UserWorkFlow;
import com.example.cadence.WorkFlows.UserWorkFlowImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.workflow.Functions;
import com.uber.cadence.workflow.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserServiceImpl implements UserService
{
    String taskList = "ENROLLMENT_TASK_LIST";

    @Autowired
    private UserActivity userActivity;

    @Override
    public String enrollStudent(String userId)
    {
        Worker.Factory factory = new Worker.Factory("local");
        Worker worker = factory.newWorker(taskList);
        worker.registerWorkflowImplementationTypes(UserWorkFlowImpl.class);
        worker.registerActivitiesImplementations(userActivity);
        factory.start();

        WorkflowClient workflowClient = WorkflowClient.newInstance("local");
        Long cur = System.currentTimeMillis();
        WorkflowOptions options = new WorkflowOptions.Builder()
                .setWorkflowId("WorkFlow_"+ cur + "_user_" + userId)
                .setExecutionStartToCloseTimeout(Duration.ofSeconds(10))
                .setTaskList(taskList)
                .build();

        UserWorkFlow workFlow = workflowClient.newWorkflowStub(UserWorkFlow.class, options);
        workFlow.createEnrollment(userId);

        return "created enrollment";
    }
}
