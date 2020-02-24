package com.example.cadence.WorkFlows;

import com.uber.cadence.workflow.WorkflowMethod;

public interface UserWorkFlow
{
    String taskList = "ENROLLMENT_TASK_LIST";

    @WorkflowMethod(name = "createEnrollmentStudent",executionStartToCloseTimeoutSeconds = 30, taskList = taskList)
    void createEnrollment(String userId);
}
