package pwd.allen.neo4j.dao;

import org.springframework.data.repository.CrudRepository;
import pwd.allen.neo4j.entity.Movie;

public interface MovieRepository extends CrudRepository<Movie, String> {

    Movie findByTitle(String title);
}
