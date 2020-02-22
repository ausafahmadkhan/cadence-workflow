package com.example.cadence.WorkFlows;

import com.uber.cadence.activity.ActivityMethod;

public interface UserActivity
{
    String taskList = "ENROLLMENT_TASK_LIST";

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2, taskList = taskList)
    String createUserEnrollment(String userId);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2, taskList = taskList)
    Void updateBalance(String userId);
}
