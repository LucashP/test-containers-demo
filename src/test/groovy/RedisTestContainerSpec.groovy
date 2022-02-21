import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import redis.clients.jedis.JedisPool
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
class RedisTestContainerSpec extends Specification {

    @Shared
    def pool

    @Shared
    def container = new GenericContainer(DockerImageName.parse("redis:6.2"))
            .withExposedPorts(6379)

    def setupSpec() {
        container.start()
        def address = container.getHost()
        def port = container.getFirstMappedPort()

        pool = new JedisPool(address, port)
    }

    def "should simple put and get"() {
        setup:
        def jedis = pool.getResource()
        jedis.set("foo", "bar")

        expect:
        jedis.get("foo") == 'bar'

        cleanup:
        pool.close()
    }
}