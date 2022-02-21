import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class RedisTestContainerTest {

    private JedisPool pool;

    @Container
    public GenericContainer container = new GenericContainer(DockerImageName.parse("redis:6.2"))
            .withExposedPorts(6379);

    @BeforeEach
    public void setUp() {
        String address = container.getHost();
        Integer port = container.getFirstMappedPort();

        pool = new JedisPool(address, port);
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