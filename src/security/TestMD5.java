package security;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jullev
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestMD5 { 

    private static final MessageDigest MESSAGE_DIGEST;
    public static final String[] EMPTY_ARRAY = new String[0];

    static {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException err) {
            throw new IllegalStateException();
        }
        MESSAGE_DIGEST = md;
    }
    private static final String HEX_CHARS = "0123456789ABCDEF";

    public String getMD5(String source) {
        byte[] bytes;
        try {
            bytes = source.getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException ue) {
            throw new IllegalStateException(ue);
        }
        byte[] result;
        synchronized (MESSAGE_DIGEST) {
            MESSAGE_DIGEST.update(bytes);
            result = MESSAGE_DIGEST.digest();
        }
        char[] resChars = new char[32];
        int len = result.length;
        for (int i = 0; i < len; i++) {
            byte b = result[i];
            int lo4 = b & 0x0F;
            int hi4 = (b & 0xF0) >> 4;
            resChars[i * 2] = HEX_CHARS.charAt(hi4);
            resChars[i * 2 + 1] = HEX_CHARS.charAt(lo4);
        }
        return new String(resChars);
    }

    public String getHash32(String source) throws UnsupportedEncodingException {
        String md5 = getMD5(source);
        return md5.substring(0, 8);
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder buf = new StringBuilder();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }

    public String getHash64(String source) throws UnsupportedEncodingException {
        String md5 = getMD5(source);
        return md5.substring(0, 32);
    }

    
}
