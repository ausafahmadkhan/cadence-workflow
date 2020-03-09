package com.example.cadence.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class JedisWrapper
{
    @Bean
    public Jedis getJedisClient()
    {
        return new Jedis();
    }
}
