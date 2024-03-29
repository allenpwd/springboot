package pwd.allen.neo4j.entity.doc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePageQuery {
    /**
     * 当前页码, 默认第一页
     */
    @ApiModelProperty("页码")
    private int current = 1;
    /**
     * 每页展示的数据数量，默认每页展示 10 条数据
     */
    @ApiModelProperty("每页展示数量")
    private int size = 10;
}
