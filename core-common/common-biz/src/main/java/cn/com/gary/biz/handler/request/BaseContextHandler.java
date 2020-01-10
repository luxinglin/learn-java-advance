package cn.com.gary.biz.handler.request;

import cn.com.gary.model.constants.CommonConstants;
import cn.com.gary.util.ToyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static cn.com.gary.model.constants.CommonConstants.CONTEXT_KEY_USER_ID;


/**
 * Created by ace on 2017/9/8.
 *
 * @author luxinglin
 */
@Slf4j
public class BaseContextHandler {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getUserID() {
        Object value = get(CONTEXT_KEY_USER_ID);
        return returnObjectValue(value);
    }

    public static void setUserID(String userID) {
        set(CONTEXT_KEY_USER_ID, userID);
    }

    public static String getUsername() {
        Object value = get(CommonConstants.CONTEXT_KEY_USERNAME);
        return returnObjectValue(value);
    }

    public static void setUsername(String username) {
        set(CommonConstants.CONTEXT_KEY_USERNAME, username);
    }

    public static String getName() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_NAME);
        return ToyUtil.getObjectValue(value);
    }

    public static void setName(String name) {
        set(CommonConstants.CONTEXT_KEY_USER_NAME, name);
    }

    public static String getToken() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_TOKEN);
        return ToyUtil.getObjectValue(value);
    }

    public static void setToken(String token) {
        set(CommonConstants.CONTEXT_KEY_USER_TOKEN, token);
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class BaseContextHandlerTest {

        @Test
        public void testSetContextVariable() throws InterruptedException {
            BaseContextHandler.set(CONTEXT_KEY_USER_ID, "main");
            new Thread(() -> {
                BaseContextHandler.set(CONTEXT_KEY_USER_ID, "moo");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Assert.assertEquals("moo", BaseContextHandler.get(CONTEXT_KEY_USER_ID));
                log.info("thread one done!");
            }).start();
            new Thread(() -> {
                BaseContextHandler.set(CONTEXT_KEY_USER_ID, "moo2");
                Assert.assertEquals("moo2", BaseContextHandler.get(CONTEXT_KEY_USER_ID));
                log.info("thread two done!");
            }).start();
            new Thread(() -> {
                BaseContextHandler.set(CONTEXT_KEY_USER_ID, "moo3");
                Assert.assertEquals("moo3", BaseContextHandler.get(CONTEXT_KEY_USER_ID));
                log.info("thread three done!");
            }).start();
            new Thread(() -> {
                BaseContextHandler.set(CONTEXT_KEY_USER_ID, "moo4");
                Assert.assertEquals("moo4", BaseContextHandler.get(CONTEXT_KEY_USER_ID));
                log.info("thread four done!");
            }).start();

            Thread.sleep(5000);
            Assert.assertEquals(BaseContextHandler.get(CONTEXT_KEY_USER_ID), "main");
            log.info("main one done!");
        }

        @Test
        public void testSetUserInfo() {
            BaseContextHandler.setUserID("test1");
            Assert.assertEquals(BaseContextHandler.getUserID(), "test1");
            BaseContextHandler.setUsername("test2");
            Assert.assertEquals(BaseContextHandler.getUsername(), "test2");
        }
    }
}
