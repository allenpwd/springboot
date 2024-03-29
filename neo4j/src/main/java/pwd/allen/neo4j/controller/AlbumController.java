package pwd.allen.neo4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pwd.allen.neo4j.entity.doc.Album;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.entity.dto.AddAlbumDTO;
import pwd.allen.neo4j.entity.dto.AlbumUpdateDTO;
import pwd.allen.neo4j.entity.dto.PageAlbumDTO;
import pwd.allen.neo4j.entity.vo.AlbumDetailVO;
import pwd.allen.neo4j.service.AlbumService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
@RestController
@RequestMapping("/admin/album")
@Api(tags = "专辑模板")
public class AlbumController {

    @Resource
    private AlbumService albumService;

    @GetMapping("/select/list")
    @ApiOperation("全部专辑")
    public MyResponse<List<Album>> findAll(){
        return albumService.findAll();
    }

    @GetMapping("/findByTitle")
    @ApiOperation("根据名字查询")
    public MyResponse<Album> findByTitle(String title){
        return albumService.findByTitle(title);
    }


    @GetMapping("/select/one")
    @ApiOperation("模糊查询")
    public MyResponse<List<Album>> findContainTitle(String title){
        return albumService.findContainTitle(title);
    }

    @PostMapping("/page")
    @ApiOperation("分页查询")
    public MyResponse<Page<Album>> pageList(@RequestBody PageAlbumDTO pageAlbumDTO){
        return albumService.pageList(pageAlbumDTO);
    }

    @PostMapping("add")
    @ApiOperation("添加专辑")
    public MyResponse addAlbum(@RequestBody AddAlbumDTO albumDTO){
        return albumService.add(albumDTO);
    }

    @PostMapping("/delete")
    @ApiOperation("删除专辑")
    public MyResponse deleteAlbum(Long id){
        return albumService.deleteAlbum(id);
    }

    @GetMapping("/detail")
    @ApiOperation("专辑详情")
    public MyResponse<AlbumDetailVO> detail(int id){
        return albumService.detail(id);
    }

    @PostMapping("/update")
    @ApiOperation("更新专辑")
    public MyResponse update(@RequestBody AlbumUpdateDTO albumUpdateDTO){
        return albumService.update(albumUpdateDTO);
    }
}
