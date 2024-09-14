package pwd.allen.minio.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 *
 * https://blog.51cto.com/u_16213459/10685241
 *
 * @author pwdan
 * @create 2024-08-07 17:14
 **/
@Slf4j
@Component
public class S3Template implements IFileService {

    @Autowired
    private AmazonS3 s3Client;

    @Override
    public void createBucket(String bucketName) {
        try {
            if (!s3Client.doesBucketExist(bucketName)) {
                s3Client.createBucket(bucketName);
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
    @Override
    public String getObjectURL(String bucketName, String objectName, int expires) {
        try {
            return s3Client.generatePresignedUrl(bucketName, objectName, new Date(expires * 1000L)).getPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public InputStream getObject(String bucketName, String objectName) {
        try {
            S3Object s3Object = s3Client.getObject(bucketName, objectName);
            Map<String, String> userMetadata = s3Object.getObjectMetadata().getUserMetadata();
            return s3Object.getObjectContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putObject(String bucketName, String objectName, InputStream stream) {
        this.putObject(bucketName, objectName, stream, null, null);
    }

    @Override
    public void putObject(String bucketName, String objectName, InputStream stream, Integer size, String contextType) {
        ObjectMetadata metadata = new ObjectMetadata();
        if (size != null) {
            metadata.setContentLength(size);
        }
        if (contextType != null) {
            metadata.setContentType(contextType);
        }
        s3Client.putObject(bucketName, objectName, stream, metadata);
    }

    @Override
    public void removeObject(String bucketName, String objectName) {
        try {
            s3Client.deleteObject(bucketName, objectName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
