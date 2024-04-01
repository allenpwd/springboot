package pwd.allen.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("Person")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    private String name;
    private Integer born;
}
