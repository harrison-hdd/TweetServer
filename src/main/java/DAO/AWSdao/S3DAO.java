package DAO.AWSdao;

import DAO.IDAO.IProfilePictureDAO;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class S3DAO implements IProfilePictureDAO {
    private static final String BUCKET_NAME = "cs340tweeter-profile-pictures";
    private static final String IMAGE_FORMAT = ".jpg";

    @Override
    public String uploadPicture(String username, byte[] imgByteArr) {
        InputStream imageStream = new ByteArrayInputStream(imgByteArr);

        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-1")
                .build();

        String keyName = username + IMAGE_FORMAT;
        s3.putObject(BUCKET_NAME, keyName, imageStream, null);

        return "https://" + BUCKET_NAME + ".s3-accelerate.amazonaws.com/" + keyName;
    }
}
