package pwd.allen.minio;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import pwd.allen.minio.config.MyMinioProperties;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通过AmazonS3 实现 分片上传 断点续传
 *
 * @author pwdan
 * @create 2024-10-28 16:32
 **/
@SpringBootTest(classes = MinioMain.class)
public class S3Test {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private MyMinioProperties minioProperties;

    private String bucketName;

    private String key;

    @BeforeEach
    public void init() {
        bucketName = minioProperties.getBucketName();
        key = StrUtil.format("{}/{}.{}", DateUtil.format(new Date(), "YYYY-MM-dd"), IdUtil.randomUUID(), ".yml");
    }

    /**
     * 创建上传请求，获取uploadId
     */
    @Test
    public void createUpload() {
        String contentType = MediaTypeFactory.getMediaType(key).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        InitiateMultipartUploadResult initiateMultipartUploadResult = amazonS3
                .initiateMultipartUpload(new InitiateMultipartUploadRequest(bucketName, key).withObjectMetadata(objectMetadata));
        String uploadId = initiateMultipartUploadResult.getUploadId();
    }

    /**
     * 判断上传是否已完成，未完成则查询已上传的分片
     */
    @Test
    public void getRequest() {
        String uploadId = "";
        boolean doesObjectExist = amazonS3.doesObjectExist(minioProperties.getBucketName(), key);
        if (!doesObjectExist) {
            // 未上传完，返回已上传的分片
            ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
            PartListing partListing = amazonS3.listParts(listPartsRequest);
            System.out.println(partListing);
        }
    }

    /**
     * 获取预签名上传地址
     * 这个地址的作用：前端直接将分片内容通过PUT请求调用该地址来上传分片
     */
    @Test
    public void getPreSignUploadUrl() {
        Integer partNumber = 1;
        String uploadId = "";
        Map<String, String> params = new HashMap<>();
        params.put("partNumber", partNumber.toString());
        params.put("uploadId", uploadId);
        // 预签名url过期时间(ms)
        Date expireDate = DateUtil.offsetMillisecond(new Date(), 10 * 60 * 1000);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key)
                .withExpiration(expireDate).withMethod(HttpMethod.PUT);
        if (params != null) {
            params.forEach((key, val) -> request.addRequestParameter(key, val));
        }
        URL preSignedUrl = amazonS3.generatePresignedUrl(request);
        System.out.println(preSignedUrl);
    }

    /**
     * 分片都上传完毕后，合并分片
     */
    @Test
    public void merge() {
        String uploadId = "";
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = amazonS3.listParts(listPartsRequest);
        List<PartSummary> parts = partListing.getParts();
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest()
                .withUploadId(uploadId)
                .withKey(key)
                .withBucketName(bucketName)
                .withPartETags(parts.stream().map(partSummary -> new PartETag(partSummary.getPartNumber(), partSummary.getETag())).collect(Collectors.toList()));
        CompleteMultipartUploadResult result = amazonS3.completeMultipartUpload(completeMultipartUploadRequest);
    }
}
