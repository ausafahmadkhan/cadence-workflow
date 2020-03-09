package com.example.cadence.Clients;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AwsSqsClient
{
    private String accessKey;
    private String secretKey;

    @Autowired
    public AwsSqsClient(@Value("${aws.access.key}") String accessKey, @Value("${aws.secret.key}") String secretKey)
    {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Bean
    public AmazonSQSAsync getAwsSqsClient()
    {
        System.out.println(accessKey + "\t______");
        System.out.println(secretKey + "\t______");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey );
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_1).build();
    }
}
