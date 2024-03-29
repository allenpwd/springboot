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
@ApiModel("添加歌曲入参")
public class AddSongDTO {

    @ApiModelProperty("歌曲名称")
    private String title;

    @ApiModelProperty("时长")
    private String duration;

    @ApiModelProperty("歌曲所属专辑")
    private String albumTitle;
}
