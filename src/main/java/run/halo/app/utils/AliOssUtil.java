package run.halo.app.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import run.halo.app.exception.FileOperationException;
import run.halo.app.model.properties.AliOssProperties;
import run.halo.app.service.OptionService;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
@Component
public class AliOssUtil {

    private final OptionService optionService;
    private String endPoint;
    private String accessKey;
    private String accessSecret;

    public AliOssUtil(OptionService optionService) {
        this.optionService = optionService;
        this.endPoint =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ENDPOINT).toString();
        this.accessKey =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ACCESS_KEY).toString();
        this.accessSecret =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ACCESS_SECRET).toString();
    }

    public void upload(Path path, String bucketName){
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, accessSecret);

        try {
            final PutObjectResult putObjectResult = ossClient.putObject(bucketName,
                path.getFileName().toString(),
                new FileInputStream(path.toString()));
            if (putObjectResult == null) {
                throw new FileOperationException("上传文件 " + path.getFileName() + " 到阿里云失败 ");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    public void delete(@NonNull String key) {
        Assert.notNull(key, "File key must not be blank");
        String bucketName =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_BUCKET_NAME).toString();

        // Init OSS client
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, accessSecret);

        try {
            ossClient.deleteObject(new DeleteObjectsRequest(bucketName).withKey(key));
        } catch (Exception e) {
            throw new FileOperationException("附件 " + key + " 从阿里云删除失败", e);
        } finally {
            ossClient.shutdown();
        }
    }
}
