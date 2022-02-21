import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class MySqlContainersTest {

    @Container
    public static MySQLContainer container = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql:8.0.25"))
            .withExposedPorts(3306);

    @Test
    public void simpleTest() {
        var url = container.getJdbcUrl();
        var user = container.getUsername();
        var password = container.getPassword();

        try (var c = getConnection(url, user, password)) {
            createTableIfNotExists(c, "simple_entity");
            assertTrue(checkIfTableExists(c, "'simple_entity'"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfTableExists(Connection c, String tableName) throws SQLException {
        var sql = "SELECT count(*) as count FROM information_schema.tables WHERE table_name=" + tableName + ";";
        var rs = c.prepareStatement(sql).executeQuery();
        var result = false;
        while (rs.next()) {
            result = rs.getInt("count") == 1;
        }
        return result;
    }

    private void createTableIfNotExists(Connection c, String tableName) throws SQLException {
        var sql = "create table if not exists " + tableName + " (id bigint auto_increment primary key, full_name varchar(255) null);";
        c.prepareStatement(sql).execute();
    }
}