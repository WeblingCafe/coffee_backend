package webling.coffee.backend.global.context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserContext {

    private static final ThreadLocal<UserAuthentication> USER_AUTHENTICATION = new ThreadLocal<>();

    public static UserAuthentication getAuthentication () {
        UserAuthentication userAuthentication = USER_AUTHENTICATION.get();
        log.info(String.format("Get User Authentication : [%s], %s", userAuthentication.getUsername(), userAuthentication.getEmail()));
        return userAuthentication;
    }

    public static void setAuthentication (UserAuthentication userAuthentication) {
        log.info(String.format("Set User Authentication : [%s], %s", userAuthentication.getUsername(), userAuthentication.getEmail()));
        USER_AUTHENTICATION.set(userAuthentication);
    }

    public static void clearAuthentication () {
        log.info("Clear User Authentication");
        USER_AUTHENTICATION.remove();
    }
}
