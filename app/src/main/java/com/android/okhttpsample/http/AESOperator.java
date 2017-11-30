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
//        if (isBase64) {
//            return Base64.encodeToString(encrypted, Base64.DEFAULT);// 此处使用BASE64做转码。 
//        } else {
//           return parseByte2HexStr(encrypted);
//            StringBuilder builder = new StringBuilder();
//            for (byte b : encrypted)
//                builder.append(String.format("%02x", b));
//            return builder.toString();
//           return Base64.encodeToString(encrypted,Base64.NO_WRAP);
//            return new String(encrypted,"utf-8");
//        }
    }

//    private static String encodeHex(byte[] data) {
//        if (data == null) {
//            return null;
//        }
//
//        StringBuilder sbuilder = new StringBuilder(data.length * 2);
//
//        for (int i=0;i<data.length;i++) {
//            if (((int) data[i] & 0xff) < 0x10) {
//                sbuilder.append("0");
//            }
//
//            sbuilder.append(Long.toString((int)data[i] & 0xff, 16));
//        }
//
//        return sbuilder.toString().toUpperCase();
//    }


//    /**
//
//     * 将byte数组转换成16进制String
//
//     * @param buf
//
//     * @return
//
//     */

//    public static String parseByte2HexStr(byte buf[]) {
//
//        StringBuffer sb = new StringBuffer();
//
//        for (int i = 0; i < buf.length; i++) {
//
//            String hex = Integer.toHexString(buf[i] & 0xFF);
//
//            if (hex.length() == 1) {
//
//                hex = '0' + hex;
//
//            }
//
//            sb.append(hex.toUpperCase());
//
//        }
//
//        return sb.toString();
//
//    }


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

//    public String decrypt(String sSrc, String key, String ivs) throws Exception {
//        return decryptBase(sSrc, key, ivs, true);
//    }

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
//            if (isBase64) {
//                encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);// 先用base64解密 
//            } else {
//                encrypted1 = Base64.decode(sSrc, Base64.NO_WRAP);
//            }
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

//    public static String encodeBytes(byte[] bytes) {
//        StringBuffer strBuf = new StringBuffer();
//
//        for (int i = 0; i < bytes.length; i++) {
//            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
//            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
//        }
//
//        return strBuf.toString();
//    }


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
