package cn.com.gary.biz.token.service;

import cn.com.gary.biz.dto.TokenUserDTO;
import cn.com.gary.biz.token.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * token工具类
 *
 * @author muzhongliang
 * @version 1.0.0
 * @since 2018-09-25添加ip地址测试打印，同时规范化代码
 */
@Component
@Slf4j
public class TokenService {
    private final TokenJedisCache tokenJedisCache;

    @Autowired
    public TokenService(TokenJedisCache tokenJedisCache) {
        this.tokenJedisCache = tokenJedisCache;
    }

    /**
     * 初始化登录token
     *
     * @param loginUser
     * @param tokeRedisTimeout
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public String initToken(TokenUserDTO loginUser, int tokeRedisTimeout) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        Map<String, String> tokenParam = new HashMap<>();
        tokenParam.put(TokenUtil.USER_ID, String.valueOf(loginUser.getUserId()));
        tokenParam.put(TokenUtil.ORG_ID, String.valueOf(loginUser.getOrgId()));
        tokenParam.put(TokenUtil.IP, String.valueOf(loginUser.getLastLoginIp()));
        String token = TokenUtil.genToken(tokenParam);
        tokenJedisCache.addToken(token, loginUser, tokeRedisTimeout);
        return token;
    }

    public void deleteToken(String token) {
        tokenJedisCache.deleteToken(token);
    }

}
