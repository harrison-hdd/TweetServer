package DAO.AWSdao.util;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

public class CredentialsProvider {
    public static AwsCredentialsProvider getProvider(){
        return ProfileCredentialsProvider.create(); //for dev
//        return EnvironmentVariableCredentialsProvider.create(); //for deployment
    }
}
