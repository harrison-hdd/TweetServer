package lambda;

import com.amazonaws.services.lambda.runtime.*;
import request.RegisterRequest;
import response.RegisterResponse;
import service.UserService;

public class Register implements RequestHandler<RegisterRequest, RegisterResponse> {
    private static final String BUCKET_NAME = "cs340tweeter-profile-pictures";
    private static final String IMAGE_FORMAT = ".jpg";

    @Override
    public RegisterResponse handleRequest(RegisterRequest registerRequest, Context context) {
        return new UserService().register(registerRequest);
    }



//    public RegisterResponse register(RegisterRequest registerRequest){
//        String username = registerRequest.getUsername();
//        String password = registerRequest.getPassword();
//        String firstName = registerRequest.getFirstName();
//        String lastName = registerRequest.getLastName();
//        String imageBase64 = registerRequest.getImageBase64();
//        if(firstName == null || firstName.equals("") ||
//                lastName == null || lastName.equals("") ||
//                username == null || username.equals("") ||
//                password == null || password.equals("") ||
//                imageBase64 == null || imageBase64.equals("")){
//            throw new RuntimeException("[Bad Request] no field can be empty");
//        }
//
//        if(!username.startsWith("@")){
//            throw new RuntimeException("[Bad Request] Username must start with @");
//        }
//
//        //check if user already exist
//
//
//        //upload
//        String pictureURL = null;
//        try{
//            pictureURL = uploadImage(username, registerRequest.getImageBase64());
//        }catch (AmazonServiceException e){
//            throw new RuntimeException("[Internal Server Error] Failed to upload image");
//        }
//
//        User user = FakeData.getInstance().getFirstUser();
//        AuthToken authToken = FakeData.getInstance().getAuthToken();
//        return new RegisterResponse(true, null, user, authToken);
//    }
//
//    private String uploadImage(String username, String imgBase64){
//        byte[] image = Base64.getDecoder().decode(imgBase64);
//        InputStream imageStream = new ByteArrayInputStream(image);
//
//        AmazonS3 s3 = AmazonS3ClientBuilder
//                .standard()
//                .withRegion("us-east-1")
//                .build();
//
//        String keyName = username + IMAGE_FORMAT;
//        s3.putObject(BUCKET_NAME, keyName, imageStream, null);
//
//        return "https://" + BUCKET_NAME + ".s3.amazonaws.com/" + keyName;
//    }
    public static void main(String[] args) { //fixme: test. Comment out later
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("abc");
        request.setLastName("abc");
        request.setUsername("@abc");
        request.setPassword("a");
        request.setImageBase64("imageBase64");

        Context c = new Context() {
            @Override
            public String getAwsRequestId() {
                return null;
            }

            @Override
            public String getLogGroupName() {
                return null;
            }

            @Override
            public String getLogStreamName() {
                return null;
            }

            @Override
            public String getFunctionName() {
                return null;
            }

            @Override
            public String getFunctionVersion() {
                return null;
            }

            @Override
            public String getInvokedFunctionArn() {
                return null;
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return null;
            }
        };
        new lambda.Register().handleRequest(request, c);

    }
}
