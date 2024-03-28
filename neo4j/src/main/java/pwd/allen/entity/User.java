package pwd.allen.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * @author pwdan
 * @create 2024-03-28 17:05
 **/
@Node
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
