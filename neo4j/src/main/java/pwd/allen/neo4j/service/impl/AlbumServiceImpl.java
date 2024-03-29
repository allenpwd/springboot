package pwd.allen.neo4j.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pwd.allen.neo4j.dao.AlbumRepository;
import pwd.allen.neo4j.dao.ArtistRepository;
import pwd.allen.neo4j.dao.SongRepository;
import pwd.allen.neo4j.entity.doc.Album;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.doc.Song;
import pwd.allen.neo4j.entity.dto.AddAlbumDTO;
import pwd.allen.neo4j.entity.dto.AlbumUpdateDTO;
import pwd.allen.neo4j.entity.dto.PageAlbumDTO;
import pwd.allen.neo4j.entity.vo.AlbumDetailVO;
import pwd.allen.neo4j.enums.ResponseCodeEnum;
import pwd.allen.neo4j.exception.BizException;
import pwd.allen.neo4j.service.AlbumService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {
    @Resource
    private AlbumRepository albumRepository;

    @Resource
    private SongRepository songRepository;

    @Resource
    private ArtistRepository artistRepository;


    @Override
    public MyResponse<List<Album>> findAll() {
        log.info("查询所有专辑列表");
        List<Album> all = albumRepository.findAll();
        return MyResponse.success(all);
    }

    @Override
    public MyResponse<Album> findByTitle(String title) {
        Album album = albumRepository.findByTitle(title);
        return MyResponse.success(album);
    }

    @Override
    public MyResponse<List<Album>> findContainTitle(String title) {

        log.info("模糊查询：{}",title);
        List<Album> albumList = albumRepository.findContainTitle(title);
        return MyResponse.success(albumList);
    }

    @Override
    public MyResponse<Page<Album>> pageList(PageAlbumDTO pageAlbumDTO) {

        int pageNumber = pageAlbumDTO.getCurrent();
        int pageSize = pageAlbumDTO.getSize();
        log.info("分页查询:{},页码：{},每页展示数量：{}",pageAlbumDTO,pageNumber,pageSize);
        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        // Page<Album> all = albumRepository.findAllByTitleContaining(pageAlbumDTO.getTitle(), pageable);
        Page<Album> all = albumRepository.page(pageAlbumDTO.getTitle(), pageAlbumDTO.getStartDate(), pageAlbumDTO.getEndDate(), pageable);

        return MyResponse.success(all);
    }

    @Override
    public MyResponse add(AddAlbumDTO albumDTO) {
        log.info("添加专辑:{}",albumDTO);
        // 线先判断专辑是否存在
        Album a = albumRepository.findByTitle(albumDTO.getTitle());
        if (Objects.nonNull(a)){
            throw new BizException(ResponseCodeEnum.ALBUM_IS_EXITS);
        }
        Album album = albumRepository.addAlbum(
                albumDTO.getTitle(),
                albumDTO.getCoverUrl(),
                albumDTO.getReleaseDate(),
                albumDTO.getArtistName()
        );
        if (Objects.isNull(album)){
            throw new BizException(ResponseCodeEnum.ADD_ALBUM_ERROR);
        }
        return MyResponse.success();

    }

    @Override
    public MyResponse deleteAlbum(Long id) {
        log.info("删除专辑:{}",id);
        // 判断专辑下面是否还有歌曲
        List<Song> songs = songRepository.findSongByAlbumId(id);
        if (!CollectionUtils.isEmpty(songs)){
            throw new BizException(ResponseCodeEnum.ALBUM_CONTAIN_SONG);
        }
        log.info(songs.toString());
        int i = albumRepository.deleteAlbumById(id);
        log.info("删除是否成功:{}",i);
        if (i<=0){
            throw new BizException(ResponseCodeEnum.DELETE_ALBUM_ERROR);
        }
        return MyResponse.success();
    }

    @Override
    public MyResponse<AlbumDetailVO> detail(int id) {
        log.info("歌曲详情id：{}",id);
        AlbumDetailVO detail = new AlbumDetailVO();


        Optional<Album> albumOptional = albumRepository.findById((long) id);
        Album album = albumOptional.orElseThrow(() -> new BizException(ResponseCodeEnum.DETAIL_ALBUM_ERROR));
        BeanUtils.copyProperties(album,detail);
        // 获取歌曲列表
        List<Song> songs = songRepository.findSongByAlbumId((long) id);
        detail.setSongs(songs);

        // 获取专辑对应艺人
        String artistName = artistRepository.getArtistName(id);
        detail.setArtistName(artistName);

        log.info("歌曲详情如下：{}",detail);
        return MyResponse.success(detail);

    }

    @Override
    @Transactional
    public MyResponse update(AlbumUpdateDTO albumUpdateDTO) {
        log.info("更新专辑:{}",albumUpdateDTO);
        Album album = albumRepository.update(
                albumUpdateDTO.getId(),
                albumUpdateDTO.getTitle(),
                albumUpdateDTO.getCoverUrl(),
                albumUpdateDTO.getReleaseDate(),
                albumUpdateDTO.getArtistName()
        );
        if (Objects.isNull(album)){
            throw new BizException(ResponseCodeEnum.UPDATE_ALBUM_ERROR);
        }
        // 补充，由于歌曲的封面依赖于专辑的封面，所以在这还需要修改歌曲的封面
        return MyResponse.success();
    }
}
