import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MySqlTest {

    @Test
    public void simpleTest() {
        var url = "jdbc:mysql://localhost:3306/my-database";
        var user = "mysql-user";
        var password = "mysql-password";

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