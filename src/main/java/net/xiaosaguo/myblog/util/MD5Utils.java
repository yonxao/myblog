package net.xiaosaguo.myblog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * description: MD5加密工具类
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
public class MD5Utils {

    public static String code(String str) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digestByteArray = md.digest();
            int i;
            StringBuilder sb = new StringBuilder();
            for (byte b : digestByteArray) {
                i = b;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            // 32位加密
            return sb.toString();
            // 16位加密
            /// return sb.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(code("111111"));
    }
}
