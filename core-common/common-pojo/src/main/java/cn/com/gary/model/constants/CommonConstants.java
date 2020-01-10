package cn.com.gary.model.constants;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-08 23:01
 **/
public interface CommonConstants {
    String COMMA = ",";
    String BOOLEAN_TRUE = "Y";
    String BOOLEAN_FALSE = "N";
    String UFT8_CHAR_SET = "UTF-8";

    //JWT
    String JWT_KEY_USER_ID = "userId";
    String JWT_KEY_ORG_ID = "orgId";
    String JWT_KEY_NAME = "name";
    String TOKEN = "token";

    //Request CONTEXT
    String CONTEXT_KEY_USER_ID = "currentUserId";
    String CONTEXT_KEY_USERNAME = "currentUserName";
    String CONTEXT_KEY_USER_NAME = "currentUser";
    String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
}
