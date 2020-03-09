package com.example.cadence.Enum;

public enum WorkFlowType
{
    UserWorkFlow("UserWorkFlow", SqsQueue.UserWorkFLowQueue);

    String val;
    SqsQueue queue;

    WorkFlowType(String val, SqsQueue queue)
    {
        this.val = val;
        this.queue = queue;
    }

    public String getVal()
    {
        return  this.val;
    }
}
