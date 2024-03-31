import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.*;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Path;

import java.util.List;

public class SessionTest {

    private Session session;

    @BeforeEach
    public void init() {
        String uri = "bolt://192.168.118.102:7687";
        String username = "neo4j";
        String password = "123456";
        Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        this.session = driver.session();
    }

    @Test
    public void test() {
        // 查询 Danny DeVito 和 Jack Nicholson 的关系（通过演出和导演两种关联关系关联），最大深度3层
        String cypherSql = "MATCH pwd=(:Person{name:\"Danny DeVito\"})-[:ACTED_IN|DIRECTED*1..3]-(:Person{name:\"Jack Nicholson\"}) RETURN pwd";
        List<Record> list = session.run(cypherSql).list();
        for (Record record : list) {
            PathValue pv = (PathValue) record.get("pwd");
            InternalPath internalPath = (InternalPath) pv.asPath();
            for (Path.Segment segment : internalPath) {
                System.out.println(String.format("%s%s-%s->%s%s", segment.start().labels(), segment.start().asMap(), segment.relationship().type(), segment.end().labels(), segment.end().asMap()));
            }
        }
    }
}
