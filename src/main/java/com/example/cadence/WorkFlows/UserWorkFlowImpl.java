package com.example.cadence.WorkFlows;

import com.uber.cadence.workflow.*;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.util.concurrent.CompletionException;

public class UserWorkFlowImpl implements UserWorkFlow
{
    private final UserActivity userActivity = Workflow.newActivityStub(UserActivity.class);
    private static Logger logger = Workflow.getLogger(UserWorkFlowImpl.class);

    @Override
    public void createEnrollment(String userId)
    {
        logger.info("Triggered workflow at " + Workflow.currentTimeMillis());
        Promise<Void> userPromise = Async.function(userActivity::createUserEnrollment, userId)
               .thenApply(((Functions.Func1<String, Void>) userActivity::updateBalance));
        userPromise.get();
     }
}
