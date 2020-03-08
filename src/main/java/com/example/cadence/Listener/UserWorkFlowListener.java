package com.example.cadence.Listener;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.example.cadence.Enum.WorkFlowQueue;
import com.example.cadence.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWorkFlowListener
{
    @Autowired
    private UserService userService;

    @Autowired
    private AmazonSQSAsync sqsClient;

    @Bean
    public void listenToUserWorkflow()
    {
        String sqsURL = sqsClient.getQueueUrl(new GetQueueUrlRequest(WorkFlowQueue.UserWorkFLowQueue.getVal())).getQueueUrl();
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(sqsURL)
                                                                .withMaxNumberOfMessages(1)
                                                                .withWaitTimeSeconds(3);
        final List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
            System.out.println("Received : " + message.getMessageId());
            System.out.println("userId : " + message.getBody());
            if (message.getBody() != null)
                userService.enrollStudent(message.getBody());
        }
    }
}
