package org.chaoyue.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@EnableConfigurationProperties({JedisConfig.JedisProperties.class,JedisConfig.RedisProperties.class})
public class JedisConfig {
    @Autowired
    private JedisProperties jedisProp;

    @Autowired
    private RedisProperties redisProp;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(jedisProp.getMaxTotal());
        config.setMaxIdle(jedisProp.getMaxIdle());
        config.setMaxWaitMillis(jedisProp.getMaxWait());

        JedisPool jedisPool;
        if("".equals(redisProp.getPassword())){
            jedisPool = new JedisPool(config, redisProp.getHost(), redisProp.getPort(), redisProp.getTimeout());
        }else{
            jedisPool = new JedisPool(config, redisProp.getHost(), redisProp.getPort(), redisProp.getTimeout(), redisProp.getPassword());
        }
        return jedisPool;
    }

    @ConfigurationProperties(prefix = RedisProperties.REDIS_PREFIX)
    class RedisProperties{

        public static final String REDIS_PREFIX = "spring.redis";

        private String host;

        private int port;

        private String password;

        private int timeout;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }

    @ConfigurationProperties(prefix = JedisProperties.JEDIS_PREFIX)
    class JedisProperties {

        public static final String JEDIS_PREFIX = "spring.redis.jedis.pool";

        private int maxTotal;

        private int maxIdle;

        private int maxWait;


        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }
    }

}
