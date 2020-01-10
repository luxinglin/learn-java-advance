package cn.com.gary.biz.token.util;

import org.apache.commons.lang3.StringEscapeUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class Des3Util {
    final static String CIPHA_KEY = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd3";

    public static void main(String[] args) throws Exception {
///	       byte[] key=new BASE64Decoder().decodeBuffer("YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd3");
///	       byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
///	       byte[] data=	    ("user=aasdfasfd&timestamp=2015-01-01 11:11:11").getBytes("UTF-8");
///	       //byte[] data="中国ABCabc123".getBytes("UTF-8");
///	       System.out.println("ECB加密解密");
///	       byte[] str3 = des3EncodeECB(key,data );
///	       byte[] str4 = ees3DecodeECB(key, str3);

///	       System.out.println(new BASE64Encoder().encode(str3));
///	       byte[] byteKey=new BASE64Decoder().decodeBuffer(new BASE64Encoder().encode(str3));
///	       System.out.println(new BASE64Encoder().encode(byteKey));
///	       System.out.println(new String(str4, "UTF-8"));
///	       System.out.println();
///	       System.out.println("CBC加密解密");
///	       byte[] str5 = des3EncodeCBC(key, keyiv, data);
///	       byte[] str6 = des3DecodeCBC(key, keyiv, str5);
///	       System.out.println(new BASE64Encoder().encode(str5));
///	       System.out.println(new String(str6, "UTF-8"));

///		    String user =  "0B22";
///		    user ="user=iluofc&timestamp=2015-12-22 17:08:11";
///		    byte[] data =user.getBytes("UTF-8");
///		    byte[] key=new BASE64Decoder().decodeBuffer("YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd3");
///
///		    byte[] userBytes=des3EncodeECB(key,data);
///
///
/////		    String ticket=new String(userBytes,"UTF-8");
///
///		    System.out.println("ticket:"+new BASE64Encoder().encode(userBytes));
///		    String ticket=new BASE64Encoder().encode(userBytes);
///
///		    byte[] deData =new BASE64Decoder().decodeBuffer(ticket);
///		    byte[] deUser=des3DecodeECB(key, deData);
///		    String deTicket=new String(deUser,"UTF-8");
        try {
            String token = genEncodeToken("1", "1", "127.0.0.1");
            System.out.println("token:" + token);
            String deToken = decodeToken(token);
            System.out.println("detoken:" + deToken);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    public static String genEncodeToken(String userId, String orgId, String ip) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] key = new BASE64Decoder().decodeBuffer(CIPHA_KEY);
        String[] uuid = UUID.randomUUID().toString().split("-");
        String tokenStr = "<user>" + userId + "</user><uuid1>" + uuid[1] + "</uuid1><orgId>" + orgId + "</orgId><uuid2>" + uuid[2] + "</uuid2><ip>" + ip + "</ip><uuid3>" + uuid[3] + "</uuid3>";
        byte[] data = tokenStr.getBytes("UTF-8");
        byte[] tokenBytes = des3EncodeECB(key, data);
        return StringEscapeUtils.escapeJava(new BASE64Encoder().encode(tokenBytes));
    }

    public static String decodeToken(String token) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] key = new BASE64Decoder().decodeBuffer(CIPHA_KEY);
        byte[] deData = new BASE64Decoder().decodeBuffer(
                StringEscapeUtils.unescapeJava(token));
        byte[] deToken = des3DecodeECB(key, deData);
        String deTokenStr = new String(deToken, "UTF-8");
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
    public static byte[] des3EncodeECB(byte[] key, byte[] data) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
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
    public static byte[] des3DecodeECB(byte[] key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
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
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
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
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

}
