package pwd.allen.neo4j.service;

import org.springframework.data.domain.Page;
import pwd.allen.neo4j.entity.doc.Album;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.dto.AddAlbumDTO;
import pwd.allen.neo4j.entity.dto.AlbumUpdateDTO;
import pwd.allen.neo4j.entity.dto.PageAlbumDTO;
import pwd.allen.neo4j.entity.vo.AlbumDetailVO;

import java.util.List;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
public interface AlbumService {
    /**
     * 查找全部专辑
     *
     * @return {@link MyResponse}<{@link List}<{@link Album}>>
     */
    MyResponse<List<Album>> findAll();

    /**
     * 按标题查找
     *
     * @param title 标题
     * @return {@link MyResponse}<{@link Album}>
     */
    MyResponse<Album> findByTitle(String title);

    /**
     * @param title 标题
     * @return {@link MyResponse}<{@link List}<{@link Album}>>
     */
    MyResponse<List<Album>> findContainTitle(String title);

    /**
     * 页面列表
     *
     * @param pageAlbumDTO 页面相册dto
     * @return {@link MyResponse}<{@link List}<{@link Album}>>
     */
    MyResponse<Page<Album>> pageList(PageAlbumDTO pageAlbumDTO);

    MyResponse add(AddAlbumDTO albumDTO);



    MyResponse deleteAlbum(Long id);

    MyResponse<AlbumDetailVO> detail(int id);

    MyResponse update(AlbumUpdateDTO albumUpdateDTO);
}
