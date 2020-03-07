package com.example.cadence.WorkFlows;

import com.example.cadence.Activities.UserActivity;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.common.RetryOptions;
import com.uber.cadence.workflow.Async;
import com.uber.cadence.workflow.Promise;
import com.uber.cadence.workflow.Saga;
import com.uber.cadence.workflow.Workflow;
import org.slf4j.Logger;

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

        //checking if it is using same activity object
        logger.info("UserActivity : {}", userActivity.getDate());
        Saga.Options options = new Saga.Options.Builder().setParallelCompensation(true).build();
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
