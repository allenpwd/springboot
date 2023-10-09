package pwd.allen.minio;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pwd.allen.minio.config.MyMinioProperties;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioTemplate {

    @Autowired
    private MyMinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    public MinioTemplate() {}

    public void createBucket(String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, ServerException, ErrorResponseException, XmlParserException, InternalException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取文件外链
     * @param bucketName bucket 名称
     * @param objectName 文件名称
     * @param expires   过期时间 <=7
     * @return
     */
    public String getObjectURL(String bucketName,String objectName,int expires) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, ErrorResponseException, ServerException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                        .object(objectName)
                        .expiry(expires)
                .build());
    }

    /**
     * 获取文件
     * @param bucketName
     * @param objectName
     * @return
     */
    public InputStream getObject(String bucketName,String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, ErrorResponseException, ServerException, XmlParserException {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 上传文件
     * @param bucketName
     * @param objectName
     * @param stream
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
    }

    public void putObject(String bucketName, String objectName, InputStream stream, int size, String contextType) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        createBucket(bucketName);
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1).contentType(contextType).build());
    }

    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    public void removeObject(String bucketName, String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, ServerException, ErrorResponseException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

}