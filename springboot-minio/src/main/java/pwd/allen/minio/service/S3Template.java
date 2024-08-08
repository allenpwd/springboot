package pwd.allen.minio.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import io.minio.GetObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
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
public class S3Template {

    @Autowired
    private AmazonS3 s3Client;

    public void putObject(String bucketName, String objectName, File file) {
        s3Client.putObject(bucketName, objectName, file);
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

    public void removeObject(String bucketName, String objectName) {
        try {
            s3Client.deleteObject(bucketName, objectName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
