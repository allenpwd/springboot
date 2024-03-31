package pwd.allen.neo4j.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * 定义关系属性实体，必须有一个标记为 @TargetNode 的字段来定义关系指向的实体
 */
@Data
@RelationshipProperties
public class Roles {
    /**
     * 必须为生成的内部ID定义一个属性，以便SDN可以在保存期间确定哪些关系可以安全地覆盖而不会丢失属性。如果SDN没有找到用于存储内部节点ID的字段，则在启动期间将失败
     * 使用 @RelationshipProperties 注释的类上唯一支持的生成ID字段是 @GeneratedValue ，使用默认ID生成器 InternalIdGenerator ，使用其他的将导致启动过程中的故障。
     */
    @Id
    @GeneratedValue
    private Long id;
    @TargetNode
    private Person actor;
    private List<String> roles;
}
