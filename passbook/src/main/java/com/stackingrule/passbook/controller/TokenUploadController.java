package com.stackingrule.passbook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h1>PassTemplate Token Upload</h1>
 */
@Slf4j
@Controller
public class TokenUploadController {

    /** Redis 客户端 **/
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public TokenUploadController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * <h2>将 token 写入 Redis</h2>
     * @param path {@link Path}
     * @param key redis key
     * @return true/false
     */
    private boolean writeTokenToRedis(Path path, String key) {

        Set<String> tokens;
        try(Stream<String> stream = Files.lines(path)) {
            tokens = stream.collect(Collectors.toSet());
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        if (!CollectionUtils.isEmpty(tokens)) {
            redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                        for (String token : tokens) {
                            connection.sAdd(key.getBytes(), token.getBytes());
                        }
                        return null;
            });

            return true;
        }
        return false;
    }
}
