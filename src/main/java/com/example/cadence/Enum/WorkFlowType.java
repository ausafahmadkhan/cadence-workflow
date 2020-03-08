package com.example.cadence.Enum;

public enum WorkFlowType
{
    UserWorkFlow("UserWorkFlow", WorkFlowQueue.UserWorkFLowQueue);

    String val;
    WorkFlowQueue queue;

    WorkFlowType(String val, WorkFlowQueue queue)
    {
        this.val = val;
        this.queue = queue;
    }

    public String getVal()
    {
        return  this.val;
    }
}
