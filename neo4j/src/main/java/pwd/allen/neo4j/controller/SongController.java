package pwd.allen.neo4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.doc.Song;
import pwd.allen.neo4j.entity.dto.AddSongDTO;
import pwd.allen.neo4j.entity.dto.PageSongDTO;
import pwd.allen.neo4j.entity.vo.SongDetailVO;
import pwd.allen.neo4j.service.SongService;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
@RestController
@RequestMapping("/admin/song")

@CrossOrigin(origins = "*")
@Api(tags = "歌曲模板")
public class SongController {

    @Resource
    private SongService songService;

    @GetMapping("select/artist")
    @ApiOperation("查询所有歌曲")
    public MyResponse<List<Song>> findAllSong(){
        return songService.findAllSong();
    }

    @GetMapping("findByTitle")
    @ApiOperation("根据歌名查询")
    public MyResponse<Song> findByTitle(String title){
        return songService.findByTitle(title);
    }

    @PostMapping("page")
    @ApiOperation("分页查询")
    public MyResponse<Page<Song>> pageList(@RequestBody PageSongDTO pageSongDTO) {
        return songService.page(pageSongDTO);
    }

    @GetMapping("findContainTitle")
    @ApiOperation("歌名模糊查询")
    public MyResponse<List<Song>> findContainTitle(String title){
        return songService.findContainTitle(title);
    }

    @PostMapping("add")
    @ApiOperation("添加歌曲")
    public MyResponse addSong(@RequestBody AddSongDTO addSongDTO){
        return songService.add(addSongDTO);
    }

    @PostMapping("delete")
    @ApiOperation("删除歌曲")
    public MyResponse deleteSong(Long id){
        return songService.deleteSong(id);
    }

    @GetMapping("/detail")
    @ApiOperation("歌曲详情")
    public MyResponse<SongDetailVO> detail(int id){
        return songService.detail(id);
    }

    @PostMapping("/update")
    @ApiOperation("更新歌曲")
    public MyResponse update(@RequestBody SongDetailVO song){
        return songService.update(song);
    }




}
