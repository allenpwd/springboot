import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import pwd.allen.neo4j.Neo4jApplication;
import pwd.allen.neo4j.entity.Movie;

@SpringBootTest(classes = Neo4jApplication.class)
public class TemplateTest {

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Test
    public void test() {
        System.out.println(neo4jTemplate.findById("叶问", Movie.class));

    }
}
