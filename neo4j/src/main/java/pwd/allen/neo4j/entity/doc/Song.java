package pwd.allen.neo4j.entity.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Node("Song")
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String title;

    @Property("release_date")
    private String releaseDate;

    @Property
    private String duration;

    @Property
    private String coverUrl;

    @Relationship(type = "SONG_ARTIST_RELATION", direction = Relationship.Direction.OUTGOING)
    private Artist artist;

}
