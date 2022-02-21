import org.testcontainers.containers.MySQLContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection
import java.sql.SQLException

import static java.sql.DriverManager.getConnection

@Testcontainers
class MySqlContainersSpec extends Specification {

    @Shared
    def container = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql:8.0.25"))
            .withExposedPorts(3306)

    def setupSpec() {
        container.start()
    }

    def "should simple test"() {
        setup:
        def url = container.getJdbcUrl()
        def user = container.getUsername()
        def password = container.getPassword()

        def c = getConnection(url, user, password)
        createTableIfNotExists(c, "simple_entity")

        expect:
        checkIfTableExists(c, "'simple_entity'")
    }

    private boolean checkIfTableExists(Connection c, String tableName) throws SQLException {
        var sql = "SELECT count(*) as count FROM information_schema.tables WHERE table_name=" + tableName + ";"
        var rs = c.prepareStatement(sql).executeQuery()
        var result = false
        while (rs.next()) {
            result = rs.getInt("count") == 1
        }
        return result
    }

    private void createTableIfNotExists(Connection c, String tableName) throws SQLException {
        var sql = "create table if not exists " + tableName + " (id bigint auto_increment primary key, full_name varchar(255) null);"
        c.prepareStatement(sql).execute()
    }
}