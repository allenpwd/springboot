package pwd.allen.neo4j.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import pwd.allen.neo4j.entity.doc.Artist;

import java.util.List;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
public interface ArtistRepository extends Neo4jRepository<Artist,Long> {

    @Query("MATCH (a:Artist) where a.name = $name return a")
    Artist findByName(@Param("name") String name);


    @Query("match (a:Artist) where a.name contains $name return a")
    List<Artist> findContainName(@Param("name") String name);

    @Query("MATCH (artist:Artist)-[:RELEASED]->(album:Album) WHERE id(album) = $id RETURN artist.name")
    String getArtistName(int id);

    @Query(value = "MATCH (a:Artist) WHERE (COALESCE($name, '') = '' OR a.name CONTAINS $name) " +
            "AND (coalesce($startDate, '') = '' OR a.birthdate >= $startDate) " +
            "AND (coalesce($endDate, '') = '' OR a.birthdate <= $endDate) " +
            "RETURN a ORDER BY DATE(a.birthdate) SKIP $skip LIMIT $limit",
            countQuery = "MATCH (a:Artist) WHERE a.name CONTAINS $name " +
                    "AND (coalesce($startDate, '') = '' OR a.birthdate >= $startDate) " +
                    "AND (coalesce($endDate, '') = '' OR a.birthdate <= $endDate) " +
                    "RETURN COUNT(a)")
    Page<Artist> page(@Param("name") String name,
                      @Param("startDate") String startDate,
                      @Param("endDate") String endDate,
                      Pageable pageable);


    @Query("CREATE (a:Artist {name: $name, gender: $gender, descent: $descent, birthdate: $birthdate, coverUrl: $coverUrl }) " +
            "return a")
    Artist addArtist(String name,String gender,String descent,String birthdate,String coverUrl);

    @Query("MATCH (artist:Artist) WHERE id(artist) = $id DELETE artist return count(*)")
    int deleteArtistById(Long id);

    @Query("MATCH (a:Artist) " +
            "WHERE id(a) = $id " +
            "SET a.name = $name, a.gender = $gender, a.descent = $descent, a.birthdate = $birthdate, a.coverUrl = $coverUrl " +
            "return a")
    Artist update(Long id, String name, String gender, String descent, String birthdate, String coverUrl);
}
