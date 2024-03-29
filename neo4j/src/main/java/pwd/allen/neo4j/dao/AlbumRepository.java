package pwd.allen.neo4j.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import pwd.allen.neo4j.entity.doc.Album;

import java.util.List;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
public interface AlbumRepository extends Neo4jRepository<Album,Long> {
    @Query("MATCH (a:Album) WHERE a.title = $title RETURN a")
    Album findByTitle(@Param("title") String title);

    @Query("MATCH (a:Album) WHERE a.title CONTAINS $title RETURN a")
    List<Album> findContainTitle(@Param("title") String title);


    Page<Album> findAll(Pageable pageable);


    // @Query(value = "MATCH (a:Album) WHERE a.title CONTAINS $title RETURN a SKIP $skip LIMIT $limit",
    //         countQuery = "MATCH (a:Album) WHERE a.title CONTAINS $title RETURN COUNT(a)")
    // Page<Album> findAllByTitleContaining(@Param("title") String title, Pageable pageable);


    @Query(value = "MATCH (a:Album) WHERE a.title CONTAINS $title " +
            "AND (coalesce($startDate, '') = '' OR a.release_date >= $startDate) " +
            "AND (coalesce($endDate, '') = '' OR a.release_date <= $endDate) " +
            "RETURN a ORDER BY DATE(a.release_date) SKIP $skip LIMIT $limit",
            countQuery = "MATCH (a:Album) WHERE a.title CONTAINS $title " +
                    "AND (coalesce($startDate, '') = '' OR a.release_date >= $startDate) " +
                    "AND (coalesce($endDate, '') = '' OR a.release_date <= $endDate) " +
                    "RETURN COUNT(a)")
    Page<Album> page(@Param("title") String title,
                     @Param("startDate") String startDate,
                     @Param("endDate") String endDate,
                     Pageable pageable);

    @Query("MATCH (artist:Artist {name: $artistName}) " +
            "CREATE (album:Album {title: $title, coverUrl: $coverUrl, release_date: $releaseDate }) " +
            "CREATE (artist)-[:RELEASED]->(album) " +
            "return album")
    Album addAlbum(String title, String coverUrl, String releaseDate, String artistName);



    @Query("MATCH (album:Album) WHERE ID(album) = $id DETACH  DELETE album RETURN count(*)")
    int deleteAlbumById(Long id);


    /**
     * @author: hua
     * @date: 2023/12/31 14:13
     * @param: null
     * @return: null
     * @description: @Query("MATCH (a:Album)-[:CONTAINS]->(s:Song) " +
     *             "WHERE ID(a) = $id " +
     *             "RETURN s")
     *     List<Song> findSongByAlbumId(Long id);
     */

    // 根据艺人id查询专辑
    @Query("MATCH (artist:Artist) -[:RELEASED]->(album:Album) " +
            "WHERE id(artist) = $id " +
            "RETURN album")
    List<Album> findAlbumsByArtistId(@Param("id") Long id);

    // 根据歌曲id查询专辑名称a:Album
    @Query("MATCH (a:Album)-[:CONTAINS]->(s:Song) WHERE id(s) = $id RETURN a.title")
    String getAlbumTitle(int id);



    // @Query("MATCH (oldArtist:Artist)-[r:RELEASED]->(album:Album) "
    //         + "MATCH (newArtist:Artist {name: $newArtistName}) "
    //         + "WHERE id(album) = $id "
    //         + "SET album.title = $title, "
    //         + "    album.coverUrl = $coverUrl, "
    //         + "    album.release_date = $releaseDate "
    //         + "CREATE (newArtist)-[:RELEASED]->(album) "
    //         + "DELETE r "
    //         + "WITH album, newArtist "
    //         + "MATCH (album)-[:CONTAINS]->(song:Song) "
    //         + "OPTIONAL MATCH (song)<-[r2:PERFORMED]-(:Artist) "
    //         + "DELETE r2 "
    //         + "CREATE (newArtist)-[:PERFORMED]->(song) "
    //         + "RETURN album limit 1")
    @Query("MATCH (oldArtist:Artist)-[r:RELEASED]->(album:Album) "
            + "MATCH (newArtist:Artist {name: $newArtistName}) "
            + "WHERE id(album) = $id "
            + "SET album.title = $title, "
            + "    album.coverUrl = $coverUrl, "
            + "    album.release_date = $releaseDate "
            + "CREATE (newArtist)-[:RELEASED]->(album) "
            + "DELETE r "
            + "WITH album, newArtist "
            + "MATCH (album)-[:CONTAINS]->(song:Song) "
            + "OPTIONAL MATCH (song)<-[r2:PERFORMED]-(:Artist) "
            + "DELETE r2 "
            + "WITH album, newArtist, song "
            + "SET song.coverUrl = $coverUrl "
            + "CREATE (newArtist)-[:PERFORMED]->(song) "
            + "WITH DISTINCT album "
            + "RETURN album")
    Album update(Long id, String title, String coverUrl, String releaseDate, String newArtistName);
}
