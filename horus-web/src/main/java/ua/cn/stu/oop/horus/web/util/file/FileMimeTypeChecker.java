package ua.cn.stu.oop.horus.web.util.file;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author alex
 */
public class FileMimeTypeChecker {
    
    private static final MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();

    public static String getFileMimeType(File f){
        return typeMap.getContentType(f);
    }
    
    public static boolean fileIsImage(File f){
        return getFileMimeType(f).startsWith("image");
    }    
}
