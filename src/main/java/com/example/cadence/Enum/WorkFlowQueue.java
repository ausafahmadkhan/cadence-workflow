package com.example.cadence.Enum;

public enum WorkFlowQueue
{
    UserWorkFLowQueue("UserWorkFLowQueue");

    String val;

    WorkFlowQueue(String val)
    {
        this.val = val;
    }

    public  String getVal()
    {
        return  this.val;
    }
}
