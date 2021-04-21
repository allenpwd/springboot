import org.flywaydb.core.Flyway;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @author lenovo
 * @create 2021-04-21 8:58
 **/
public class FlyWayTest {

    /**
     * API调用方式
     * @throws IOException
     */
    @Test
    public void exec() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties"));
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        Flyway flyway = Flyway.configure().dataSource(url, username, password)
                .locations("classpath:flyway/")
                .baselineOnMigrate(true)
                .baselineVersion("0.5")
                .load();
        flyway.migrate();
    }
}
