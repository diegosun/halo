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
    public AliOssUtil(OptionService optionService) {
        this.optionService = optionService;
    }

    public void upload(Path pathToUpload, String bucketName, String bucketDirName){
        String endPoint =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ENDPOINT).toString();
        String accessKey =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ACCESS_KEY).toString();
        String accessSecret =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ACCESS_SECRET).toString();

        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, accessSecret);

        try {
            String fileName = bucketDirName.isEmpty() ? pathToUpload.getFileName().toString() : bucketDirName+"/"+pathToUpload.getFileName();
            final PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, new FileInputStream(pathToUpload.toString()));
            if (putObjectResult == null) {
                throw new FileOperationException("上传文件 " + pathToUpload.getFileName() + " 到阿里云失败 ");
            }
            log.info("备份" + fileName +"成功");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    public void delete(String bucketName, @NonNull String key) {
        String endPoint =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ENDPOINT).toString();
        String accessKey =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ACCESS_KEY).toString();
        String accessSecret =
            optionService.getByPropertyOfNonNull(AliOssProperties.OSS_ACCESS_SECRET).toString();

        Assert.notNull(key, "File key must not be blank");

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
