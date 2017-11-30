package com.android.okhttpsample.http;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */

public class AESOperator {
    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     * 已服务端定义好的值
     */
    private String sKey = "a2e502fa9f48b5cf";//key，可自行修改
    private String ivParameter = "a2e502fa9f48b5cf";//偏移量,可自行修改
    private static AESOperator instance = null;

    public AESOperator() {

    }

    public static AESOperator getInstance() {
        if (instance == null)
            instance = new AESOperator();
        return instance;
    }

    /**
     * 加密
     *
     * @param sSrc
     * @return
     * @throws Exception
     */
    public String encrypt(String sSrc) throws Exception {
        return encrypt2String(sSrc, sKey, ivParameter);
    }

    public byte[] encryptByte(String sSrc) throws Exception {
        return encrypt2Byte(sSrc, sKey, ivParameter);
    }


    private String encrypt2String(String encData, String secretKey, String vector) throws Exception {
        byte[] data = encrypt2Byte(encData, secretKey, vector);
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    private byte[] encrypt2Byte(String encData, String secretKey, String vector) throws Exception {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        return encrypted;
    }


    /**
     * 解密
     *
     * @param sSrc
     * @return
     * @throws Exception
     */

    public String decrypt(String sSrc) throws Exception {
        byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);// 先用base64解密 
        return decryptBase(encrypted1, sKey, ivParameter);
    }

    public String decrypt(byte[] sSrc) throws Exception {
        return decryptBase(sSrc, sKey, ivParameter);
    }

    /**
     * 解密过程
     *
     * @param sSrc
     * @param key
     * @param ivs
     * @return
     * @throws Exception
     */
    private String decryptBase(byte[] sSrc, String key, String ivs) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = sSrc;
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }


    public static void main(String aregs[]) {
        String content = "啊啊坎大哈的卡扩大2342jndsfsx";

        try {
            AESOperator aes = new AESOperator();
//            String temp="7prXtbnTSDKCMUEXJCVXjpvx/5crbsLTV6TCHkSuYEg=";
//            System.out.println(aes.decrypt(temp));
//            System.exit(0);
//            //加密
            System.out.println("加密前：" + content);
            String encrypt = aes.encrypt(content);
            System.out.println("加密后：" + encrypt);
            //解密
            String decrypt = aes.decrypt(encrypt);
            System.out.println("解密后：" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
