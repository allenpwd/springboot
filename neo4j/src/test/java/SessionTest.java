import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.*;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = {SessionTest.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SessionTest {

    @Value("${spring.neo4j.uri}")
    private String uri;

    @Value("${spring.neo4j.authentication.username}")
    private String username;

    @Value("${spring.neo4j.authentication.password}")
    private String password;

    private Session session;

    @BeforeEach
    public void init() {
        Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        this.session = driver.session();
    }

    /**
     * 查询链路并打印
     */
    @Test
    public void test() {
        // 查询 Danny DeVito 和 Jack Nicholson 的关系（通过演出和导演两种关联关系关联），最大深度4层
        String cypherSql = "MATCH pwd=(:Person{name:\"Danny DeVito\"})-[:ACTED_IN|DIRECTED*1..4]-(:Person{name:\"Jack Nicholson\"}) RETURN pwd";
        List<Record> list = session.run(cypherSql).list();
        // 一条record是Danny DeVito 和 Jack Nicholson 之间的一条链路
        for (int i = 0; i < list.size(); i++) {
            Record record = list.get(i);
            PathValue pv = (PathValue) record.get("pwd");
            InternalPath internalPath = (InternalPath) pv.asPath();
            StringBuilder sb = new StringBuilder();
            for (Path.Segment segment : internalPath) {
                if (sb.length() == 0) {
                    sb.append(String.format("%s%s-%s->%s%s", segment.start().labels(), getDesc(segment.start()), segment.relationship().type(), segment.end().labels(), getDesc(segment.end())));
                } else if (segment.relationship().startNodeId() == segment.start().id()) {
                    sb.append(String.format("-%s->%s%s", segment.relationship().type(), segment.end().labels(), getDesc(segment.end())));
                } else {
                    sb.append(String.format("<-%s-%s%s", segment.relationship().type(), segment.end().labels(), getDesc(segment.end())));
                }
            }
            System.out.println(String.format("链路%d：%s", i + 1, sb.toString()));
        }
    }
    private String getDesc(Node node) {
        if (node.hasLabel("Person")) {
            return node.get("name").asString();
        } else if (node.hasLabel("Movie")) {
            return node.get("title").asString();
        } else {
            return node.asMap().toString();
        }
    }
}
