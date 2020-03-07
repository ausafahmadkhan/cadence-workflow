package com.example.cadence.Activities;

import com.uber.cadence.activity.ActivityMethod;

public interface UserActivity
{
    String taskList = "ENROLLMENT_TASK_LIST";

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2, taskList = taskList)
    String createUserEnrollment(String userId);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2, taskList = taskList)
    Void compensateUserEnrollment(String userId);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2, taskList = taskList)
    Void updateBalance(String userId);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2, taskList = taskList)
    Void compensateBalanceUpdate(String userId);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2, taskList = taskList)
    public String getDate();

}
