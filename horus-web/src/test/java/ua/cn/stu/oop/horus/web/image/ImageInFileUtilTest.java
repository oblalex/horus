package ua.cn.stu.oop.horus.web.image;

import ua.cn.stu.oop.horus.web.util.image.ImageInFileUtil;
import java.io.File;
import org.junit.Test;

/**
 *
 * @author alex
 */
public class ImageInFileUtilTest {    

    //@Test
    public void testCrop() throws Exception {
        File f = new File("/home/alex/proj/sf/horus/horus-web/tmp/zzz.jpg");
        ImageInFileUtil.crop(f, 220, 220, 45, 51);
        ImageInFileUtil.scale(f, 100, 100);
    }
}
