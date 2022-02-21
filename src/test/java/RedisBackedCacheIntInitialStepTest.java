import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedisBackedCacheIntInitialStepTest {

    private JedisPool pool;

    @BeforeEach
    public void setUp() {
        pool = new JedisPool("localhost", 6379);
    }

    @Test
    public void testSimplePutAndGet() {
        try (Jedis jedis = pool.getResource()) {
            jedis.set("foo", "bar");
            assertEquals("bar", jedis.get("foo"));
        }
        pool.close();
    }
}