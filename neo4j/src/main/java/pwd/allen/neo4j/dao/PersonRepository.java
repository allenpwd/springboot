package pwd.allen.neo4j.dao;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pwd.allen.neo4j.entity.Person;

/**
 * @author pwdan
 * @create 2024-04-01 14:10
 **/
public interface PersonRepository extends Neo4jRepository<Person, String> {
}
