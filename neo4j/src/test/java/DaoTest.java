import cn.hutool.core.collection.CollUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pwd.allen.neo4j.Neo4jApplication;
import pwd.allen.neo4j.dao.ArtistRepository;
import pwd.allen.neo4j.dao.MovieRepository;
import pwd.allen.neo4j.dao.PersonRepository;
import pwd.allen.neo4j.dao.SongRepository;
import pwd.allen.neo4j.entity.Movie;
import pwd.allen.neo4j.entity.Person;
import pwd.allen.neo4j.entity.Roles;
import pwd.allen.neo4j.entity.doc.Song;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author pwdan
 * @create 2024-03-29 16:15
 **/

@SpringBootTest(classes = Neo4jApplication.class)
public class DaoTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

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

    @Test
    public void saveRelation() {
        Optional<Movie> movie = movieRepository.findById("叶问");
        Person person1 = personRepository.findById("J.T. Walsh").get();
        Person person2 = personRepository.findById("Kevin Pollak").get();
        movie.ifPresent(m -> {
            if (CollUtil.isEmpty(m.getDirectors())) {
                m.setDirectors(Arrays.asList(person1, person2));
            }
            if (CollUtil.isEmpty(m.getActorsAndRoles())) {
                Roles roles = new Roles();
                roles.setActor(person1);
                roles.setRoles(Arrays.asList("老王", "王警官"));
                m.setActorsAndRoles(Arrays.asList(roles));
            }
            movieRepository.save(m);
        });
    }


    /**
     * 执行语句：
     * MATCH (n:`Movie`) WHERE n.title = $title RETURN n{.released, .tagline, .title, __nodeLabels__: labels(n), __internalNeo4jId__: id(n), Movie_DIRECTED_Person: [(n)<-[:`DIRECTED`]-(n_directors:`Person`) | n_directors{.born, .name, __nodeLabels__: labels(n_directors), __internalNeo4jId__: id(n_directors)}], Movie_ACTED_IN_Roles: [(n)<-[:`ACTED_IN`]-(n_actorsAndRoles:`Roles`) | n_actorsAndRoles{.actor, .roles, __nodeLabels__: labels(n_actorsAndRoles), __internalNeo4jId__: id(n_actorsAndRoles)}]}
     */
    @Test
    public void queryMovie() {
        System.out.println(movieRepository.findByTitle("A Few Good Men"));
    }

    /**
     * 执行语句：
     * MATCH (n:`Movie`) WHERE n.title = $__id__ RETURN n{.released, .tagline, .title, __nodeLabels__: labels(n), __internalNeo4jId__: id(n), Movie_DIRECTED_Person: [(n)<-[:`DIRECTED`]-(n_directors:`Person`) | n_directors{.born, .name, __nodeLabels__: labels(n_directors), __internalNeo4jId__: id(n_directors)}], Movie_ACTED_IN_Person: [(n)<-[Movie__relationship__Person:`ACTED_IN`]-(n_actorsAndRoles:`Person`) | n_actorsAndRoles{.born, .name, __nodeLabels__: labels(n_actorsAndRoles), __internalNeo4jId__: id(n_actorsAndRoles), Movie__relationship__Person}]}
     */
    @Test
    public void queryMovie2() {
        System.out.println(movieRepository.findById("A Few Good Men"));
    }

    /**
     * MATCH (n:`Movie`) RETURN n ORDER BY n.released DESC, n.title SKIP 0 LIMIT 10
     */
    @Test
    public void findAll() {
        Sort.TypedSort<Movie> movieSort = Sort.sort(Movie.class);
        Page<Movie> page = movieRepository.findAll(PageRequest.of(0, 10).withSort(movieSort.by(Movie::getReleased).descending().and(movieSort.by(Movie::getTitle))));
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent());
    }

    @Test
    public void findDistinctMovieByReleasedGreaterThanEqual() {
        Page<Movie> page = movieRepository.findDistinctMovieByReleasedGreaterThanEqualOrderByReleasedDesc(2000, PageRequest.of(0, 10));
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent());
    }

    @Test
    public void findMoviesByActorName() {
        List<Movie> movies = movieRepository.findMoviesByActorName("Laurence Fishburne");
        System.out.println(movies);
    }
    @Test
    public void findPersonsByTitle() {
        System.out.println(movieRepository.findPersonsByTitle("The Matrix"));
    }

    @Test
    public void deleteAll() {
        movieRepository.deleteAll();
    }

    @Test
    public void findFirstOrderByReleasedDesc() {
        Movie movie = movieRepository.findFirstByOrderByReleasedDesc();
        System.out.println(movie);
    }

    @Test
    public void cleanAll() {
        System.out.println(movieRepository.cleanAll());
    }

}
