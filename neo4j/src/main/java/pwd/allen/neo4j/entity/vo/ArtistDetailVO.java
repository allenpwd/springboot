package pwd.allen.neo4j.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pwd.allen.neo4j.entity.doc.Album;

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
@ApiModel("歌手详情VO")
public class ArtistDetailVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("歌手名字")
    private String name;

    @ApiModelProperty("歌手性别")
    private String gender;

    @ApiModelProperty("歌手国籍")
    private String descent;

    @ApiModelProperty("出生日期")
    private String birthdate;

    @ApiModelProperty("封面")
    private String coverUrl;

    @ApiModelProperty("专辑列表")
    private List<Album> albums;
}
