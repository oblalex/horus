package ua.cn.stu.oop.horus.web.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.util.ByteSource;

/**
 *
 * @author alex
 */
public class EncodingUtil {

    public static ByteSource getRandomSaltSource() {
        return new SecureRandomNumberGenerator().nextBytes();
    }

    public static String encodeStringUsingSaltSource(String stringSource, ByteSource saltSource) {
        return new Sha1Hash(stringSource, saltSource.getBytes()).toString();
    }
    
    public static String encodeStringUsingSaltBytes(String stringSource, byte[] bytes) {
        return new Sha1Hash(stringSource,  bytes).toString();
    }
}
