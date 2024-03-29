package pwd.allen.neo4j.service;

import org.springframework.data.domain.Page;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.doc.Song;
import pwd.allen.neo4j.entity.dto.AddSongDTO;
import pwd.allen.neo4j.entity.dto.PageSongDTO;
import pwd.allen.neo4j.entity.vo.SongDetailVO;

import java.util.List;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
public interface SongService {
    /**
     * 查找所有歌曲
     *
     * @return {@link MyResponse}<{@link List}<{@link Song}>>
     */// 获取所有歌曲
    MyResponse<List<Song>> findAllSong();

    /**
     * 按标题查找
     *
     * @param title 标题
     * @return {@link MyResponse}<{@link Song}>
     */
    MyResponse<Song> findByTitle(String title);

    /**
     * 第页
     *
     * @return {@link MyResponse}<{@link Page}<{@link Song}>>
     */
    MyResponse<Page<Song>> page(PageSongDTO pageable);

    MyResponse<List<Song>> findContainTitle(String title);

    MyResponse add(AddSongDTO addSongDTO);

    MyResponse deleteSong(Long id);

    MyResponse<SongDetailVO> detail(int id);

    MyResponse update(SongDetailVO song);
}
