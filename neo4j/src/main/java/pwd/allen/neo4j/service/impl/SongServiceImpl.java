package pwd.allen.neo4j.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwd.allen.neo4j.dao.AlbumRepository;
import pwd.allen.neo4j.dao.SongRepository;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.doc.Song;
import pwd.allen.neo4j.entity.dto.AddSongDTO;
import pwd.allen.neo4j.entity.dto.PageSongDTO;
import pwd.allen.neo4j.entity.vo.SongDetailVO;
import pwd.allen.neo4j.enums.ResponseCodeEnum;
import pwd.allen.neo4j.exception.BizException;
import pwd.allen.neo4j.service.SongService;

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
public class SongServiceImpl implements SongService {

    @Resource
    private SongRepository songRepository;


    @Resource
    private AlbumRepository albumRepository;
    /**
     * 查找所有歌曲
     *
     * @return {@link MyResponse}<{@link List}<{@link Song}>>
     */
    @Override
    public MyResponse<List<Song>> findAllSong() {
        List<Song> all = songRepository.findAll();
        log.info("获取所有歌曲");
        return MyResponse.success(all);

    }

    @Override
    public MyResponse<Song> findByTitle(String title) {
        return MyResponse.success(songRepository.findByTitle(title));
    }

    @Override
    public MyResponse<Page<Song>> page(PageSongDTO pageSongDTO) {
        int pageNumber = pageSongDTO.getCurrent();
        int pageSize = pageSongDTO.getSize();
        log.info("分页查询:{},页码：{},每页展示数量：{}",pageSongDTO,pageNumber,pageSize);
        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        // Page<Album> all = albumRepository.findAllByTitleContaining(pageAlbumDTO.getTitle(), pageable);
        Page<Song> all = songRepository.page(pageSongDTO.getTitle(), pageSongDTO.getStartDate(), pageSongDTO.getEndDate(), pageable);

        return MyResponse.success(all);

    }

    @Override
    public MyResponse<List<Song>> findContainTitle(String title) {
        log.info("模糊查询歌曲,{}",title);
        return MyResponse.success(songRepository.findContainTitle(title));

    }

    @Override
    public MyResponse add(AddSongDTO addSongDTO) {
        log.info("添加歌曲：{}",addSongDTO);
        Song song = songRepository.addSong(addSongDTO.getTitle(), addSongDTO.getDuration(), addSongDTO.getAlbumTitle());
        if (Objects.isNull(song)){
            throw new BizException(ResponseCodeEnum.ADD_SONG_ERROR);
        }
        return MyResponse.success();
    }

    @Override
    public MyResponse deleteSong(Long id) {
        log.info("删除歌曲 id：{}",id);
        int i = songRepository.deleteSongById(id);
        if (i <= 0){
            throw new BizException(ResponseCodeEnum.DEL_SONG_ERROR);
        }
        return MyResponse.success();
    }

    @Override
    public MyResponse<SongDetailVO> detail(int id) {
        log.info("歌曲详情查询:{}",id);

        Optional<Song> songOptional = songRepository.findById((long) id);
        Song song = songOptional.orElseThrow(()->new BizException(ResponseCodeEnum.DETAIL_SONG_ERROR));
        SongDetailVO detail = new SongDetailVO();
        BeanUtils.copyProperties(song,detail);
        String albumTitle = albumRepository.getAlbumTitle(id);
        detail.setAlbumTitle(albumTitle);
        return MyResponse.success(detail);
    }

    @Override
    @Transactional
    public MyResponse update(SongDetailVO song) {
        log.info("更新歌曲:{}",song);
        int i = songRepository.deleteSongById(song.getId());
        log.info("删除是否成功:{}",i);
        Song addSong = songRepository.addSong(song.getTitle(), song.getDuration(), song.getAlbumTitle());
        log.info("添加成功:{}",addSong);
        if (Objects.isNull(addSong) || i <= 0){
            throw new BizException(ResponseCodeEnum.UPDATE_SONG_ERROR);
        }
        return MyResponse.success();
    }
}
