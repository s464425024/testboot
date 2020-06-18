package org.chaoyue.demo;

import lombok.extern.slf4j.Slf4j;
import org.chaoyue.dao.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author songchaoyue
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired(required = false)
    TestMapper testMapper;

    @Autowired(required = false)
    private JedisPool jedisPool;


    @GetMapping("/test-db")
    @ResponseBody
    public Map testDb(){
        Map map = testMapper.getMap();
        log.info("=====,{}",map);
        return map;
    }

    @GetMapping("/test-redis")
    public String testRedis(){
        String key = "aaa";
        try(Jedis jedis = jedisPool.getResource()){
            log.info("active={}",jedisPool.getNumActive());
            jedis.setex(key,12,"2222");
            log.info("key={},value={}",key,jedis.get(key));
        }
        log.info("active={}",jedisPool.getNumActive());
        return "redis";
    }
}
