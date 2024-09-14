package pwd.allen.minio.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pwd.allen.minio.config.MyMinioProperties;

import java.io.InputStream;

@Component
@Slf4j
public class MinioTemplate implements IFileService {

    @Autowired
    private MyMinioProperties minioProperties;

    @Setter
    @Autowired
    private MinioClient minioClient;

    public MinioTemplate() {}

    public void createBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件外链，可供外网下载文件
     * @param bucketName bucket 名称
     * @param objectName 文件名称
     * @param expires   过期时间 单位s 默认7天
     * @return
     */
    public String getObjectURL(String bucketName, String objectName, int expires) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs
                            .builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expires)
                            .method(Method.GET)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件
     * @param bucketName
     * @param objectName
     * @return
     */
    @Override
    public InputStream getObject(String bucketName, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     * @param bucketName
     * @param objectName
     * @param stream
     */
    @Override
    public void putObject(String bucketName, String objectName, InputStream stream) {
        try {
            putObject(bucketName, objectName, stream, stream.available(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putObject(String bucketName, String objectName, InputStream stream, Integer size, String contextType) {
        if (contextType == null || "".equals(contextType)) {
            contextType = "application/octet-stream";
        }
        try {
            if (size == null) {
                size = stream.available();
            }
            createBucket(bucketName);
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1).contentType(contextType).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    @Override
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
