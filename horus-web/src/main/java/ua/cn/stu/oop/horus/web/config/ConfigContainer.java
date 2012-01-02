package ua.cn.stu.oop.horus.web.config;

import ua.cn.stu.oop.horus.core.io.xml.ObjectInXmlFile;
import ua.cn.stu.oop.horus.core.io.xml.ObjectInXmlFile.*;

/**
 *
 * @author alex
 */
public class ConfigContainer {

    private static final String pathToConfig = "./horus_config.xml";
    private static final ObjectInXmlFile configInXmlFile;
    public static final Config CONFIG = new Config();

    static {
        configInXmlFile = new ObjectInXmlFile(CONFIG, pathToConfig);
        RestoringResult result = restore();
        if (result != RestoringResult.SUCCESS) {
            reset();
            save();
        } else {
            CONFIG.TMP.restartCleanUpTimer();
        }        
    }

    public static RestoringResult restore() {
        configInXmlFile.restoreFromXmlFile();
        return configInXmlFile.getRestoringResult();
    }

    public static SavingResult save() {
        configInXmlFile.saveToXmlFile();
        if (configInXmlFile.getSavingResult().equals(SavingResult.SUCCESS)){
            CONFIG.TMP.restartCleanUpTimer();
        }
        return configInXmlFile.getSavingResult();
    }

    public static void reset() {
        CONFIG.reset();
    }
}
