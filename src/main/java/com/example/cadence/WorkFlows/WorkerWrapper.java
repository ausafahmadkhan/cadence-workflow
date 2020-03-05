package com.example.cadence.WorkFlows;

import com.uber.cadence.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WorkerWrapper
{

    private static String taskList = "ENROLLMENT_TASK_LIST";

    @Autowired
    private UserActivity userActivity;

    @PostConstruct
    public void startWorkerFactory()
    {
        Worker.Factory factory = new Worker.Factory("local");
        Worker worker = factory.newWorker(taskList);
        worker.registerWorkflowImplementationTypes(UserWorkFlowImpl.class);
        worker.registerActivitiesImplementations(userActivity);
        factory.start();
    }
}
