import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pwd.allen.neo4j.Neo4jApplication;
import pwd.allen.neo4j.dao.ArtistRepository;
import pwd.allen.neo4j.dao.SongRepository;
import pwd.allen.neo4j.entity.doc.Artist;
import pwd.allen.neo4j.entity.doc.Song;

/**
 * @author pwdan
 * @create 2024-03-29 16:15
 **/

@SpringBootTest(classes = Neo4jApplication.class)
public class DaoTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @Test
    public void saveArtist() {
        Artist artist = artistRepository.save(Artist.builder()
                .id(2L)
                .name("pwd2")
                .birthdate("2024-04-01")
                .gender("男")
                .coverUrl("http://www.baidu.com/")
                .build());
        System.out.println(artist);
    }


    @Test
    public void queryArtist() {
        Artist artist = artistRepository.findByName("pwdan");
        System.out.println(artist);
    }

    @Test
    public void deleteAllArtist() {
        artistRepository.deleteAll();
    }

    @Test
    public void saveSong() {
        Song song = Song.builder()
                .title("夜曲")
                .coverUrl("http://abc")
                .releaseDate("2024-04-01")
                .duration("04:31")
                .artist(artistRepository.findById(1L).get())
                .build();

        System.out.println(songRepository.save(song));
    }
}
