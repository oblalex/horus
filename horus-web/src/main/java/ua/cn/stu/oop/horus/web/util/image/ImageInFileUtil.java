package ua.cn.stu.oop.horus.web.util.image;

import java.io.File;
import java.io.IOException;
import org.im4java.core.*;

/**
 *
 * @author alex
 */
public class ImageInFileUtil {

    private static final ConvertCmd cmd = new ConvertCmd();   
    
    public static void crop(File f, int width, int height, int x, int y) throws IOException, InterruptedException, IM4JavaException{
        IMOperation op = new IMOperation();
        op.addImage(f.getAbsolutePath());
        op.crop(width, height, x, y, "!");
        op.p_repage();
        op.addImage(f.getAbsolutePath());
        cmd.run(op);
    }
    
    public static void scale(File f, int width, int height) throws IOException, InterruptedException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage(f.getAbsolutePath());
        op.scale(width, height);
        op.addImage(f.getAbsolutePath());

        cmd.run(op);
    }
}
