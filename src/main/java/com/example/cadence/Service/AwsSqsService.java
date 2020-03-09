package com.example.cadence.Service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.cadence.Enum.SqsQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AwsSqsService
{
    @PostConstruct
    public void createQueue()
    {
        try {
            CreateQueueRequest createQueueRequest = new CreateQueueRequest().withQueueName(SqsQueue.UserWorkFLowQueue.getVal());
            //createQueueRequest.addAttributesEntry(QueueAttributeName.FifoQueue.name(), "true");
            createQueueRequest.addAttributesEntry(QueueAttributeName.DelaySeconds.name(), "1");
            sqsClient.createQueue(createQueueRequest);
        }
        catch (Exception e)
        {
            System.out.println("Error initialising queue : " + e.getMessage());
        }
    }

    @Autowired
    private AmazonSQSAsync sqsClient;

    public void sendMessage(String payload, SqsQueue sqsQueue)
    {
        GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(sqsQueue.getVal());
        String queueUrl = sqsClient.getQueueUrl(getQueueUrlRequest).getQueueUrl();
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setQueueUrl(queueUrl);
//        sendMessageRequest.setMessageGroupId(UUID.randomUUID().toString());
//        sendMessageRequest.setMessageDeduplicationId(UUID.randomUUID().toString());
        sendMessageRequest.setMessageBody(payload);
        sqsClient.sendMessage(sendMessageRequest);
    }
}
