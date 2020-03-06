package com.example.cadence.WorkFlows;

import com.uber.cadence.ActivityTaskStartedEventAttributes;
import com.uber.cadence.activity.Activity;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.activity.ActivityTask;
import com.uber.cadence.common.RetryOptions;
import com.uber.cadence.workflow.Async;
import com.uber.cadence.workflow.Promise;
import com.uber.cadence.workflow.Saga;
import com.uber.cadence.workflow.Workflow;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

public class UserWorkFlowImpl implements UserWorkFlow
{
    private final UserActivity userActivity = Workflow.newActivityStub(UserActivity.class,
            new ActivityOptions.Builder()
                                .setRetryOptions(new RetryOptions.Builder()
                                                .setInitialInterval(Duration.ofSeconds(1))
                                                .setExpiration(Duration.ofMinutes(1))
                                                .setBackoffCoefficient(1.5d)
                                                .setMaximumAttempts(4)
                                                .build())
                                .build());

    private static Logger logger = Workflow.getLogger(UserWorkFlowImpl.class);

    @Override
    public void createEnrollment(String userId)
    {
        logger.info("Triggered workflow at " + Workflow.currentTimeMillis());
        logger.info("Workflow info : {}", Workflow.getWorkflowInfo());
        logger.info("UserActivity : {}", userActivity.getDate() );
        Saga.Options options = new Saga.Options.Builder().setParallelCompensation(false).build();
        Saga saga = new Saga(options);

        try {
            Promise<String> enrollmentPromise = Async.function(userActivity::createUserEnrollment, userId);
            saga.addCompensation(userActivity::compensateUserEnrollment, userId);
            enrollmentPromise.get();

            Promise<Void> balancePromise = Async.function(userActivity::updateBalance, userId);
            saga.addCompensation(userActivity::compensateBalanceUpdate, userId);
            balancePromise.get();
        }
        catch (Exception e )
        {
            saga.compensate();
            logger.error("Compensated : Wrapped exc :  " + e.getMessage());
        }
     }
}
