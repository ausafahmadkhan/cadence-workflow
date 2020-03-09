package com.example.cadence.Configuration;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.Session;

@Configuration
@EnableJms
public class JmsConfiguration
{
    private String accessKey;
    private String secretKey;
    private SQSConnectionFactory connectionFactory;

    @Autowired
    public JmsConfiguration(@Value("${aws.access.key}") String accessKey, @Value("${aws.secret.key}") String secretKey)
    {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey );
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        this.connectionFactory = SQSConnectionFactory.builder()
                                    .withRegion(Region.getRegion(Regions.US_EAST_1))
                                    .withAWSCredentialsProvider(credentialsProvider)
                                    .build();
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(this.connectionFactory);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate() {
        return new JmsTemplate(this.connectionFactory);
    }



}
