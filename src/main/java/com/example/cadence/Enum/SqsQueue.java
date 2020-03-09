package com.example.cadence.Enum;

public enum SqsQueue
{
    UserWorkFLowQueue("UserWorkFLowQueue");

    String val;

    SqsQueue(String val)
    {
        this.val = val;
    }

    public  String getVal()
    {
        return  this.val;
    }
}
