package com.example.cadence.WorkFlows;

import com.uber.cadence.workflow.*;
import lombok.SneakyThrows;

import java.util.concurrent.CompletionException;

public class UserWorkFlowImpl implements UserWorkFlow
{
    private final UserActivity userActivity = Workflow.newActivityStub(UserActivity.class);

    @Override
    public void createEnrollment(String userId)
    {
        System.out.println(userActivity.toString() + "\t user : " + userId);
        Promise<Void> userPromise = Async.function(userActivity::createUserEnrollment, userId)
               .thenApply(((Functions.Func1<String, Void>) userActivity::updateBalance));
        System.out.println("Enrollment created");
        //userActivity.createUserEnrollment(userId);
    }
}
