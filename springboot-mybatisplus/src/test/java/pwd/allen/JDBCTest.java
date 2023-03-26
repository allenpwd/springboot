package pwd.allen;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author 门那粒沙
 * @create 2020-06-06 19:20
 **/
@Slf4j
public class JDBCTest {

    private Connection connection;

    @BeforeEach
    public void init() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties"));

        // mysql
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        // oracle
//        url = properties.getProperty("oracle.url");
//        username = properties.getProperty("oracle.username");
//        password = properties.getProperty("oracle.password");

        //可以省略，JDBC 4.0以后 DriverManager会加载并初始化jar里的Driver（SPI机制）
//        Class.forName(properties.getProperty("jdbc.driver"));

        // 获取连接
        connection = DriverManager.getConnection(url, username, password);
        log.info("数据库名：{}", connection.getMetaData().getDatabaseProductName());
        log.info("数据库版本：{}   【{} {}】", connection.getMetaData().getDatabaseProductVersion()
                , connection.getMetaData().getDatabaseMinorVersion()
                , connection.getMetaData().getDatabaseMajorVersion());
        log.info("Driver：{}", connection.getMetaData().getDriverName());
        log.info("supportsTransactions：{}", connection.getMetaData().supportsTransactions());
    }

    @AfterEach
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * 测试查询
     *
     * @throws SQLException
     */
    @Test
    public void one() throws SQLException {
        String sql = "select * from test.db_user where id=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, 1);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            //大小写都可以
            System.out.println(rs.getString("USER_NAME"));
            System.out.println(rs.getString("create_at"));
        }
        rs.close();
        ps.close();
    }

    @Test
    public void transcation() throws SQLException {
        // 设置不自动提交，即开启事务
        connection.setAutoCommit(false);

        String sql = "UPDATE test.db_user set user_name=? where id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "测试回滚咯咯");
        pstmt.setInt(2, 1);
        log.info("更新：{}", pstmt.executeUpdate());

        try {
            System.out.println(1 / 0);

            // 提交
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚
            connection.rollback();
        }
    }
}
