package pwd.allen.neo4j.service;

import org.springframework.data.domain.Page;
import pwd.allen.neo4j.entity.doc.Artist;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.dto.AddArtistDTO;
import pwd.allen.neo4j.entity.dto.ArtistUpdateDTO;
import pwd.allen.neo4j.entity.dto.PageArtistDTO;
import pwd.allen.neo4j.entity.vo.ArtistDetailVO;

import java.util.List;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
public interface ArtistService {
    /**
     * 按名称查找
     *
     * @param name 姓名
     * @return {@link Artist}
     */
    MyResponse<Artist> findByName(String name);

    /**
     * 查找全部
     *
     * @return {@link MyResponse}<{@link List}<{@link Artist}>>
     */
    MyResponse<List<Artist>> findAll();


    /**
     * 模糊查询
     * @param name 姓名
     * @return {@link MyResponse}<{@link List}<{@link Artist}>>
     */
    MyResponse<List<Artist>> findContainName(String name);

    MyResponse<Page<Artist>> pageList(PageArtistDTO pageArtistDTO);

    MyResponse add(AddArtistDTO addArtistDTO);

    MyResponse deleteArtist(Long id);

    MyResponse<ArtistDetailVO> detail(Long id);

    MyResponse update(ArtistUpdateDTO artistUpdateDTO);

}
