package pwd.allen.neo4j.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
@ApiModel("编辑专辑DTO")
public class AlbumUpdateDTO {
    private Long id;

    @ApiModelProperty("专辑名称")
    private String title;

    @ApiModelProperty("专辑封面")
    private String coverUrl;

    @ApiModelProperty("发布时间")
    private String releaseDate;

    @ApiModelProperty("专辑所属艺人")
    private String artistName;
}
