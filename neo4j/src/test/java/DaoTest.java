import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pwd.allen.neo4j.Neo4jApplication;
import pwd.allen.neo4j.dao.ArtistRepository;
import pwd.allen.neo4j.dao.MovieRepository;
import pwd.allen.neo4j.dao.SongRepository;
import pwd.allen.neo4j.entity.Movie;
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

    @Autowired
    private MovieRepository movieRepository;

    /**
     * 执行语句是：MERGE (n:`Movie` {title: $__id__}) SET n += $__properties__ RETURN n
     * title相同的记录会更新，没有则新增
     */
    @Test
    public void saveMovie() {
        Movie movie = movieRepository.save(Movie.builder()
                        .title("叶问")
                        .released(2008)
                .description("1930年代，中国武术之都佛山。\n" +
                        "　　这日，武功了得的北方武师金山找（樊少皇 饰）率众来佛山踢馆，各大武馆馆主皆成他手下败将之后，他找上习得一身武艺却为人低调未设馆授徒的叶问（甄子丹 饰）。在巡警李钊（林家栋 饰）、好友武痴林（行宇 饰）等乡邻的请求及妻子张永成（熊黛林 饰 ）的鼓励下，叶问用咏春拳以柔克刚将金山找制服，确立在佛山的大师傅地位，李钊更私下将他视作师父。之前叶问挚友周清泉（任达华 饰）有心拉他合开纺织厂，但怕与爱妻及爱子分开的他只在资金上将周支持。\n" +
                        "　　几年之后，侵华日军将佛山攻占，叶问同幸存下的乡邻一样，被迫携妻儿移居废屋。生计面前，叶问混在人群争抢去煤矿做苦力的机会，李钊则成为日军翻译官。日军将领三蒲（池内博之 饰）是名武痴，他以一袋白米作奖赏，派李钊四处搜寻能打国人在打斗场上与日军切磋，多名昔日武馆馆主及武痴林因此惨死。这将叶问激怒，他走入打斗场。").build());
        System.out.println(movie);
    }


    /**
     * 执行语句：
     * MATCH (n:`Movie`) WHERE n.title = $title RETURN n{.released, .tagline, .title, __nodeLabels__: labels(n), __internalNeo4jId__: id(n), Movie_DIRECTED_Person: [(n)<-[:`DIRECTED`]-(n_directors:`Person`) | n_directors{.born, .name, __nodeLabels__: labels(n_directors), __internalNeo4jId__: id(n_directors)}], Movie_ACTED_IN_Roles: [(n)<-[:`ACTED_IN`]-(n_actorsAndRoles:`Roles`) | n_actorsAndRoles{.actor, .roles, __nodeLabels__: labels(n_actorsAndRoles), __internalNeo4jId__: id(n_actorsAndRoles)}]}
     */
    @Test
    public void queryMovie() {
        System.out.println(movieRepository.findByTitle("A Few Good Men"));
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
