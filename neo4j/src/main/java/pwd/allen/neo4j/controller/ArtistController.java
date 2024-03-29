package pwd.allen.neo4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pwd.allen.neo4j.entity.doc.Artist;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.dto.AddArtistDTO;
import pwd.allen.neo4j.entity.dto.ArtistUpdateDTO;
import pwd.allen.neo4j.entity.dto.PageArtistDTO;
import pwd.allen.neo4j.entity.vo.ArtistDetailVO;
import pwd.allen.neo4j.service.ArtistService;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
@RestController
@RequestMapping("admin/artist")
@CrossOrigin("*")
@Api(tags = "歌手模板")
public class ArtistController {

    @Resource
    private ArtistService artistService;

    @GetMapping("/select/list")
    @ApiOperation("查询全部歌手")
    public MyResponse<List<Artist>> findAll(){
        return artistService.findAll();
    }

    @GetMapping("/findByName")
    @ApiOperation("根据名字查询")
    public MyResponse<Artist> findByName(String name){
        return artistService.findByName(name);
    }

    @GetMapping("/select/one")
    @ApiOperation("模糊查询")
    public MyResponse<List<Artist>> findContainName(String name){
        return artistService.findContainName(name);
    }

    @PostMapping("/page")
    @ApiOperation("分页查询")
    public MyResponse<Page<Artist>> pageList(@RequestBody PageArtistDTO pageArtistDTO){
        return artistService.pageList(pageArtistDTO);
    }

    @PostMapping("/add")
    @ApiOperation("添加歌手")
    public MyResponse addArtist(@RequestBody AddArtistDTO addArtistDTO){
        return artistService.add(addArtistDTO);
    }

    @PostMapping("/delete")
    @ApiOperation("删除歌手")
    public MyResponse deleteArtist(Long id){
        return artistService.deleteArtist(id);
    }

    @GetMapping("/detail")
    @ApiOperation("歌手详情")
    public MyResponse<ArtistDetailVO> detail(Long id){
        return artistService.detail(id);
    }

    @PostMapping("/update")
    @ApiOperation("编辑歌手")
    public MyResponse update(@RequestBody ArtistUpdateDTO artistUpdateDTO){
        return artistService.update(artistUpdateDTO);
    }



}
