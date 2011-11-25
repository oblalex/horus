package ua.cn.stu.oop.horus.web.file;

import ua.cn.stu.oop.horus.web.util.file.FileMimeTypeChecker;
import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Test;

/**
 *
 * @author alex
 */
public class FileTypeCheckerTest {    

    //@Test
    public void testFileIsImage() {
        
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    File f = new File("/home/alex/Pictures/bg.jpg");
                    assertEquals(true, FileMimeTypeChecker.fileIsImage(f));        
                }
            }).start();
        }        
    }
}
