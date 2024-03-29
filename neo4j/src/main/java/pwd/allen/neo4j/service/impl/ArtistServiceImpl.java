package pwd.allen.neo4j.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pwd.allen.neo4j.dao.AlbumRepository;
import pwd.allen.neo4j.dao.ArtistRepository;
import pwd.allen.neo4j.entity.doc.Album;
import pwd.allen.neo4j.entity.doc.Artist;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.dto.AddArtistDTO;
import pwd.allen.neo4j.entity.dto.ArtistUpdateDTO;
import pwd.allen.neo4j.entity.dto.PageArtistDTO;
import pwd.allen.neo4j.entity.vo.ArtistDetailVO;
import pwd.allen.neo4j.enums.ResponseCodeEnum;
import pwd.allen.neo4j.exception.BizException;
import pwd.allen.neo4j.service.ArtistService;

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
public class ArtistServiceImpl implements ArtistService {
    @Resource
    private ArtistRepository artistRepository;

    @Resource
    private AlbumRepository albumRepository;
    @Override
    public MyResponse<Artist> findByName(String name) {
        Artist artist = artistRepository.findByName(name);
        log.info("根据名字查询歌手:{}",name);
        return MyResponse.success(artist);
    }

    @Override
    public MyResponse<List<Artist>> findAll() {
        List<Artist> all = artistRepository.findAll();
        log.info("查询所有歌手");
        return MyResponse.success(all);
    }

    @Override
    public MyResponse<List<Artist>> findContainName(String name) {
        List<Artist> containName = artistRepository.findContainName(name);
        return MyResponse.success(containName);
    }

    @Override
    public MyResponse<Page<Artist>> pageList(PageArtistDTO pageArtistDTO) {
        int pageNumber = pageArtistDTO.getCurrent();
        int pageSize = pageArtistDTO.getSize();
        log.info("分页查询:{},页码：{},每页展示数量：{}",pageArtistDTO,pageNumber,pageSize);
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Artist> all = artistRepository.page(pageArtistDTO.getName(), pageArtistDTO.getStartDate(), pageArtistDTO.getEndDate(), pageable);

        return MyResponse.success(all);
    }

    @Override
    public MyResponse add(AddArtistDTO addArtistDTO) {
        log.info("添加歌手：{}",addArtistDTO);
        // CREATE (邓紫棋:Artist {name: "G.E.M.邓紫棋", gender: "女", descent: "中国", birthdate: "1981-08-16", coverUrl: "http://106.55.229.181:9000/neo4j/新地球.jpg"})
        Artist artist = artistRepository.addArtist(
                addArtistDTO.getName(),
                addArtistDTO.getGender(),
                addArtistDTO.getDescent(),
                addArtistDTO.getBirthdate(),
                addArtistDTO.getCoverUrl()
        );
        if (Objects.isNull(artist)){
            throw new BizException(ResponseCodeEnum.ADD_ARTIST_ERROR);
        }
        return MyResponse.success();
    }

    @Override
    public MyResponse deleteArtist(Long id) {
        log.info("删除歌手：id:{}",id);
        // 判断专辑下面是否还有专辑
        List<Album> albums = albumRepository.findAlbumsByArtistId(id);
        if (!CollectionUtils.isEmpty(albums)){
            throw new BizException(ResponseCodeEnum.ARTIST_CONTAIN_ALBUM);
        }
        log.info(albums.toString());
        int i = artistRepository.deleteArtistById(id);
        log.info("删除是否成功:{}",i);
        if (i<=0){
            throw new BizException(ResponseCodeEnum.DELETE_ARTIST_ERROR);
        }
        return MyResponse.success();

    }

    @Override
    public MyResponse<ArtistDetailVO> detail(Long id) {
        log.info("歌手详情：id：{}",id);
        //

        ArtistDetailVO detail = new ArtistDetailVO();

        Optional<Artist> artistOptional = artistRepository.findById(id);
        Artist artist = artistOptional.orElseThrow(() -> new BizException(ResponseCodeEnum.DETAIL_ARTIST_ERROR));
        BeanUtils.copyProperties(artist,detail);
        // 获取专辑列表
        List<Album> albums = albumRepository.findAlbumsByArtistId(id);
        detail.setAlbums(albums);

        log.info("歌手详情如下：{}",detail);
        return MyResponse.success(detail);
    }

    @Override
    public MyResponse update(ArtistUpdateDTO dto) {
        log.info("更新歌手：{}",dto);

        Artist artist = artistRepository.update(
                dto.getId(),
                dto.getName(),
                dto.getGender(),
                dto.getDescent(),
                dto.getBirthdate(),
                dto.getCoverUrl()
        );
        if (Objects.isNull(artist)){
            throw new BizException(ResponseCodeEnum.UPDATE_ARTIST_ERROR);
        }
        return MyResponse.success();
    }
}
