package com.ecommerce.app.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisConfig {
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(java.time.Duration.ofMinutes(10)) // Set the default expiration time for cache entries
                .disableCachingNullValues() // Disable caching of null values
                .serializeValuesWith(RedisSerializationContext.SerializationPair.
                        fromSerializer(new GenericJackson2JsonRedisSerializer())); // Use JSON serialization for cache values
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
//                .transactionAware()
                .build();
    }
}
