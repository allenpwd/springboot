package pwd.allen.neo4j.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import pwd.allen.neo4j.entity.doc.Song;
import pwd.allen.neo4j.entity.vo.SongDetailVO;

import java.util.List;



/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
public interface SongRepository extends Neo4jRepository<Song,Long> {
    @Query("MATCH (s:Song) WHERE s.title = $title RETURN s")
    Song findByTitle(@Param("title")String title);

    @Query("MATCH (s:Song) WHERE s.title CONTAINS $title RETURN s")
    List<Song> findContainTitle(@Param("title") String title);

    @Query("MATCH (song:Song {title: $songTitle})<-[:PERFORMED]-(artist:Artist), (song)<-[:CONTAINS]-(album:Album) RETURN song, artist, album")
    List<Object> findSongWithAlbumAndArtist(String songTitle);

    Page<Song> findAll(Pageable pageable);

    @Query(value = "MATCH (s:Song) WHERE s.title CONTAINS $title " +
            "AND (coalesce($startDate, '') = '' OR s.release_date >= $startDate) " +
            "AND (coalesce($endDate, '') = '' OR s.release_date <= $endDate) " +
            "RETURN s ORDER BY DATE(s.release_date) ASC SKIP $skip LIMIT $limit",
            countQuery = "MATCH (s:Song) WHERE s.title CONTAINS $title " +
                    "AND (coalesce($startDate, '') = '' OR s.release_date >= $startDate) " +
                    "AND (coalesce($endDate, '') = '' OR s.release_date <= $endDate) " +
                    "RETURN COUNT(s)")
    Page<Song> page(@Param("title") String title,
                     @Param("startDate") String startDate,
                     @Param("endDate") String endDate,
                     Pageable pageable);

    @Query("MATCH (album:Album {title: $albumTitle}) " +
            "MATCH (artist:Artist)-[:RELEASED]->(album) " +
            "CREATE (song:Song {title: $title, duration: $duration, coverUrl: album.coverUrl, release_date: album.release_date}) " +
            "CREATE (album)-[:CONTAINS]->(song) " +
            "CREATE (artist)-[:PERFORMED]->(song) " +
            "RETURN song")
    Song addSong(String title, String duration, String albumTitle);


    @Query("MATCH (song:Song) WHERE ID(song) = $id DETACH  DELETE song RETURN count(*)")
    int deleteSongById(Long id);

    // "
    @Query("MATCH (a:Album) -[:CONTAINS]-> (s:Song) WHERE id(s) = $id RETURN id(s) AS id, s.title AS title, s.duration AS duration, a.title AS albumTitle")
    SongDetailVO songDetail(int id);

    // 查询专辑下面的歌曲
    @Query("MATCH (a:Album)-[:CONTAINS]->(s:Song) " +
            "WHERE ID(a) = $id " +
            "RETURN s")
    List<Song> findSongByAlbumId(Long id);

}
