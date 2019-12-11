package c.g.a.x.lib_support.utils;

import java.security.MessageDigest;

public final class MD5Utils {

    public static String encrypt(byte[] bytes) throws Exception {

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        messageDigest.reset();
        messageDigest.update(bytes);
        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (byte b : byteArray) {
            if (Integer.toHexString(0xFF & b).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & b));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & b));
        }
        return md5StrBuff.toString();

    }

    public static String encrypt2(byte[] source) {
        String s = null;

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();

            char[] str = new char[16 * 2];

            int k = 0;
            for (int i = 0; i < 16; i++) {

                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];

                str[k++] = hexDigits[byte0 & 0xf];
            }

            s = new String(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("a".getBytes()));
    }
}
