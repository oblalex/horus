package ua.cn.stu.oop.horus.web.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

/**
 *
 * @author alex
 */
public class EncodingUtilTest {    

    private final static Log log = LogFactory.getLog(EncodingUtilTest.class);

    //@Test
    public void testEncodeStringUsingSaltSource() {
        ByteSource bs =  EncodingUtil.getRandomSaltSource();
        log.debug(bs.getBytes());
        String encodedString = EncodingUtil.encodeStringUsingSaltSource("1111", bs);
        log.debug(encodedString);
    }
}
