package pwd.allen.neo4j.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pwd.allen.neo4j.entity.doc.Song;

import java.util.List;


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
@ApiModel("专辑详情VO")
public class AlbumDetailVO {

    private Long id;

    @ApiModelProperty("专辑名称")
    private String title;

    @ApiModelProperty("专辑封面")
    private String coverUrl;

    @ApiModelProperty("发布日期")
    private String releaseDate;

    @ApiModelProperty("专辑艺人")
    private String artistName;

    @ApiModelProperty("专辑歌曲")
    private List<Song> songs;
}
