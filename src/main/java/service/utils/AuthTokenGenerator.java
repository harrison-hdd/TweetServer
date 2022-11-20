package service.utils;

import edu.byu.cs.tweeter.model.domain.AuthToken;

import java.util.Date;
import java.util.UUID;

public class AuthTokenGenerator {
    private static final int _1_DAY = 86400 * 100; //milliseconds in 1 day

    public static AuthToken newAuthToken(){
        return new AuthToken(UUID.randomUUID().toString(),
                String.valueOf(new Date().getTime() + _1_DAY)); //expires one day from now
    }
}
