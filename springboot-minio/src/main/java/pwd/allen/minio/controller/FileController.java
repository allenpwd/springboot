package pwd.allen.minio.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pwd.allen.minio.config.MyMinioProperties;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 文件管理请求处理
 */
@Api("文件服务")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MyMinioProperties minioProperties;

    @Autowired
    private MinioClient client;

    /**
     * 文件上传请求
     * @param file 文件
     */
    @ApiOperation("上传")
    @PostMapping("upload")
    public Object upload(@NotNull @RequestParam MultipartFile file) {

        String contentType = file.getContentType();
//        contentType = MediaTypeFactory.getMediaType(file.getName()).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();

        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(contentType)
                    .build();
            ObjectWriteResponse objectWriteResponse = client.putObject(args);
            return objectWriteResponse;
        } catch (Exception e) {
            log.error(e.toString(), e);
            return e.toString();
        }
    }


    /**
     * 删除文件
     * @param filePath
     */
    @ApiOperation("删除")
    @GetMapping("deleteFiles")
    public Object deleteFiles(@RequestParam("filePath") String filePath) {
        try {
            final RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .objects(CollUtil.toList(new DeleteObject(filePath)))
                    .build();

            // 删除文件
            final Iterable<Result<DeleteError>> results = client.removeObjects(removeObjectsArgs);
            return results;
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return e.toString();
        }
    }


    /**
     * 下载
     * @param response
     * @param filePath
     */
    @ApiOperation("下载")
	@GetMapping(value="/download")
	public void download(HttpServletResponse response, @RequestParam("filePath") String filePath)  {
        String fileName = filePath.split("/")[filePath.split("/").length - 1];
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
            inputStream = client.getObject(
                    GetObjectArgs.builder().bucket(minioProperties.getBucketName()).object(filePath).build());
            if (inputStream != null) {
                response.setCharacterEncoding("utf8");
                response.setContentType("application/x-download");
                //设置文件名称
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
                //缓冲字节输出流
                outputStream = new BufferedOutputStream(response.getOutputStream());
                IoUtil.copy(inputStream, outputStream);
                outputStream.flush();
            } else {
                // 资源不存在
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
		} catch(Exception e) {
			log.error("下载失败", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			IoUtil.close(inputStream);
            IoUtil.close(outputStream);
		}
	}

}
