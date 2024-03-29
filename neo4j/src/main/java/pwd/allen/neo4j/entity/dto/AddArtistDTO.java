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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("添加歌手入参")
public class AddArtistDTO {
    @ApiModelProperty("歌手名字")
    private String name;

    @ApiModelProperty("歌手封面")
    private String coverUrl;

    @ApiModelProperty("歌手性别")
    private String gender;

    @ApiModelProperty("歌手国籍")
    private String descent;

    @ApiModelProperty("出生日期")
    private String birthdate;

}
