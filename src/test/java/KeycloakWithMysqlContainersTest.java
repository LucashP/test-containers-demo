import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class KeycloakWithMysqlContainersTest {

    public static Network NETWORK = Network.newNetwork();

    @Container
    public static GenericContainer MYSQL_CONTAINER = new GenericContainer<>(DockerImageName.parse("mysql:5.7"))
            .withNetwork(NETWORK)
            .withNetworkAliases("mysql")
            .withEnv("MYSQL_ROOT_PASSWORD", "root")
            .withEnv("MYSQL_DATABASE", "keycloak")
            .withEnv("MYSQL_USER", "keycloak")
            .withEnv("MYSQL_PASSWORD", "password")
            .withExposedPorts(3306);

    @Container
    public static GenericContainer KEYCLOAK_CONTAINER = new GenericContainer<>(DockerImageName.parse("jboss/keycloak:3.4.3.Final"))
            .dependsOn(MYSQL_CONTAINER)
            .withNetwork(NETWORK)
            .withEnv("DB_VENDOR", "MYSQL")
            .withEnv("DB_PORT", "3306")
            .withEnv("DB_ADDR", "mysql")
            .withEnv("DB_DATABASE", "keycloak")
            .withEnv("DB_USER", "keycloak")
            .withEnv("DB_PASSWORD", "password")
            .withEnv("KEYCLOAK_USER", "admin")
            .withEnv("KEYCLOAK_PASSWORD", "password")
            .withEnv("MYSQL_PORT_3306_TCP_ADDR", "mysql")
            .withEnv("MYSQL_PORT_3306_TCP_PORT", "3306")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/auth/"));

    @Test
    public void simpleTest() {
        assertTrue(MYSQL_CONTAINER.isRunning());
        assertTrue(KEYCLOAK_CONTAINER.isRunning());
    }
}