package pwd.allen.minio.service;

import java.io.InputStream;

/**
 * @author pwdan
 * @create 2024-09-14 15:24
 **/
public interface IFileService {


    void createBucket(String bucketName);

    String getObjectURL(String bucketName, String objectName, int expires);

    InputStream getObject(String bucketName, String objectName);

    void putObject(String bucketName, String objectName, InputStream stream);

    void putObject(String bucketName, String objectName, InputStream stream, Integer size, String contextType);

    void removeObject(String bucketName, String objectName);
}
