package pwd.allen.neo4j.entity.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel("编辑歌手DTO")
public class ArtistUpdateDTO {

    private Long id;

    private String name;

    private String gender;

    private String descent;

    private String birthdate;

    private String coverUrl;
}
