package ua.cn.stu.oop.horus.web.util.pages.validator;

import java.io.File;
import org.springframework.stereotype.Component;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.WebMessages;

/**
 *
 * @author alex
 */
@Component
public class DirectoryPathValidator extends GenericValidator {

    @Override
    public String validateAndGetErrorMessageOrNull(AvailableLocale locale, Object... obj) {
        String path = (String) obj[0];
        
        if (path == null) {
            return WebMessages.getMessage("path.undef", locale);
        }
        
        File f = new File(path);
        
        if (f.exists()==false){
            return WebMessages.getMessage("path.exists.not", locale);
        }
        
        if (f.isDirectory()==false){
            return WebMessages.getMessage("path.non.directory", locale);
        }
        
        if (f.canWrite()==false){
            return WebMessages.getMessage("directory.writable.not", locale);
        }
        
        return null;
    }
    
}
