package pwd.allen.neo4j.entity.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
//    @GeneratedValue     // 打开这个后id会变成空，然后每次save都创建一个新的
    private Long id;

    @Property
    private String name;

    @Property
    private String gender;

    @Property
    private String descent;

    @Property
    private String birthdate;

    @Property
    private String coverUrl;
}
