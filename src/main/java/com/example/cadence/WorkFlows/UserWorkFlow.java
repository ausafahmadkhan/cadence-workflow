package com.example.cadence.WorkFlows;

import com.uber.cadence.workflow.WorkflowMethod;

public interface UserWorkFlow
{
    String taskList = "ENROLLMENT_TASK_LIST";

    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = taskList)
    void createEnrollment(String userId);
}
