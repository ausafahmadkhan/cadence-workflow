package com.example.cadence.Worker;

import com.example.cadence.Activities.UserActivity;
import com.example.cadence.WorkFlows.UserWorkFlowImpl;
import com.uber.cadence.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WorkerWrapper
{

    private static String taskList = "ENROLLMENT_TASK_LIST";
    private static String DOMAIN = "local";

    @Autowired
    private UserActivity userActivity;

    @PostConstruct
    public void startWorkerFactory()
    {
        Worker.Factory factory = new Worker.Factory(DOMAIN);
        Worker worker = factory.newWorker(taskList);
        worker.registerWorkflowImplementationTypes(UserWorkFlowImpl.class);
        worker.registerActivitiesImplementations(userActivity);
        factory.start();
    }
}
