package ua.cn.stu.oop.horus.web.util.file;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author alex
 */
public class HorusFileUtilsTest {

    @Test
    public void testCleanDirectory() throws IOException {
        File d = new File("/home/alex/Downloads/Mono2.0 (2)");
        
        if (d.exists()==false){
            return;
        }
        
        long ttl = 900*1000;
        
        System.out.println("Was: "+d.listFiles().length);        
        HorusFileUtils.cleanDirectory(d, ttl);
        System.out.println("Now: "+d.listFiles().length);
    }
}
