package cn.com.gary.biz.token.util;

import cn.com.gary.model.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.UUID;

/**
 * DES3 util
 *
 * @author gary
 * @since 2020-02-18
 */
@Slf4j
public class Des3Util {
    private final static String CIPHER_KEY = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd3";
    private final static String DES_EDE = "desede";

    public static void main(String[] args) throws Exception {
        try {
            String token = genToken("1", "12", "127.0.0.1");
            System.out.println("token:" + token);
            String deToken = decodeToken(token);
            System.out.println("detoken:" + deToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param userId
     * @param orgId
     * @param ip
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static String genToken(String userId, String orgId, String ip) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] key = Base64.getDecoder().decode(CIPHER_KEY);
        String[] uuid = UUID.randomUUID().toString().split("-");
        String tokenStr = "<user>" + userId + "</user><uuid1>" + uuid[1] + "</uuid1><orgId>" + orgId + "</orgId><uuid2>" + uuid[2] + "</uuid2><ip>" + ip + "</ip><uuid3>" + uuid[3] + "</uuid3>";
        byte[] data = tokenStr.getBytes(CommonConstants.UFT8_CHAR_SET);
        byte[] tokenBytes = des3EncodeECB(key, data);
        return StringEscapeUtils.escapeJava(Base64.getEncoder().encodeToString(tokenBytes));
    }

    /**
     * @param token
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static String decodeToken(String token) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] key = Base64.getDecoder().decode((CIPHER_KEY));
        byte[] deData = Base64.getDecoder().decode((StringEscapeUtils.unescapeJava(token)));
        byte[] deToken = des3DecodeECB(key, deData);
        String deTokenStr = new String(deToken, CommonConstants.UFT8_CHAR_SET);
        return deTokenStr;
    }

    /**
     * ECB加密,不要IV
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws Exception
     */
    static byte[] des3EncodeECB(byte[] key, byte[] data) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DES_EDE);
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(DES_EDE + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    /**
     * ECB解密,不要IV
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    static byte[] des3DecodeECB(byte[] key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DES_EDE);
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(DES_EDE + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    /**
     * CBC加密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  明文
     * @return Base64编码的密文
     * @throws Exception
     */
    static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DES_EDE);
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(DES_EDE + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    /**
     * CBC解密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DES_EDE);
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(DES_EDE + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }
}
