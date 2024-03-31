package pwd.allen.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @create 2020-05-08 16:08
 **/
@Node("Movie")  // 定义labels，默认是类名
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    /**
     * 每个实体都要有唯一的id，如果实体没有唯一的属性，可以使用@Id注解+@GeneratedValue使用neo4j内置的id生成器生成
     */
    @Id
//    @GeneratedValue
    private String title;

    @Property("tagline")   // 如果属性名与字段名不一致，可以使用@Property注解指定
    private String description;

    private Integer released;

    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
    private List<Roles> actorsAndRoles;

    @Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
    private List<Person> directors = new ArrayList<>();

    /**
     * 会将没定义的其他标签放在这里
     * 执行的语句：MATCH (n) WHERE n.title = $__id__ UNWIND labels(n) AS label WITH label WHERE NOT (label IN $__staticLabels__) RETURN collect(label) AS __nodeLabels__
     * TODO 报空指针，如果您有其他应用程序向节点添加其他标签，请不要使用 @DynamicLabels 。如果 @DynamicLabels 出现在托管实体上，则结果标签集将被写入数据库。
     */
//    @DynamicLabels
//    private List<String> dynamicLabels = new ArrayList<>();
}
