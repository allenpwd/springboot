package pwd.allen.neo4j.dao;

import org.neo4j.driver.internal.value.PathValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pwd.allen.neo4j.entity.Movie;
import pwd.allen.neo4j.entity.Person;

import java.util.List;

public interface MovieRepository extends Neo4jRepository<Movie, String> {

    Movie findByTitle(String title);

    Page<Movie> findDistinctMovieByReleasedGreaterThanEqualOrderByReleasedDesc(Integer released, Pageable pageable);

    /**
     *
     * @param name
     * @return
     */
    @Query("MATCH (p:Person)-[:ACTED_IN]->(m:Movie) WHERE p.name = $name RETURN m")
    List<Movie> findMoviesByActorName(String name);

    /**
     * 报错：org.springframework.data.neo4j.core.mapping.NoRootNodeMappingException: Could not find mappable nodes or relationships inside Record<{p: node<8>}> for org.springframework.data.neo4j.core.mapping.DefaultNeo4jPersistentEntity@79add732
     * TODO
     * @param title
     * @return
     */
    @Query("MATCH (p:Person)-[:ACTED_IN]->(m:Movie) WHERE m.title = $title RETURN p")
    List<Person> findPersonsByTitle(String title);

    long countByTitle(String title);

    Movie findFirstByOrderByReleasedDesc();

    /**
     * 删除所有Movie节点 及关联关系（加上DETACH会联通删除关联节点的关系）
     *
     * 删除id=175的电影及其关联关系
     * MATCH (n:Movie) where id(n)=175 DETACH DELETE n RETURN count(*)
     *
     * @return
     */
    @Query("MATCH (n:Movie) DETACH DELETE n RETURN count(*)")
    long cleanAll();

    /**
     * 添加或者更新ACTED_IN关系（如果电影或者演员不存在，则关系不会创建）
     *
     * @param title
     * @param name
     * @param roles
     * @return 一个PathValue是一个relationship链路
     */
    // 写法一
//    @Query("MATCH (p:Person{name:$name}),(m:Movie{title:$title})\n" +
//            "MERGE rel=(p)-[r:ACTED_IN]->(m)\n" +
//            "ON CREATE SET r.roles=$roles\n" +
//            "ON MATCH SET r.roles=$roles\n" +
//            "return rel")
    // 写法二
    @Query("MATCH (p:Person{name:$name}),(m:Movie{title:$title})\n" +
            "MERGE rel=(p)-[r:ACTED_IN]->(m)\n" +
            "SET r+={roles:$roles}\n" +
            "return rel")
    List<PathValue> addActedIn(String title, String name, List<String> roles);

}
