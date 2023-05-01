package webling.coffee.backend.global.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static webling.coffee.backend.global.constant.S3FilePath.FOLDER_DIVISION;

@Configuration
@RequiredArgsConstructor
public class S3Upload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folderName;

    private final AmazonS3 amazonS3;

    public String upload (MultipartFile multipartFile) throws IOException {
        String s3FileName = folderName + FOLDER_DIVISION + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

}
