package pwd.allen.neo4j.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pwd.allen.neo4j.entity.Movie;
import pwd.allen.neo4j.entity.Person;

import java.util.List;
import java.util.Map;

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

    @Query("MATCH (n) DETACH DELETE n RETURN count(*)")
    long cleanAll();
}
