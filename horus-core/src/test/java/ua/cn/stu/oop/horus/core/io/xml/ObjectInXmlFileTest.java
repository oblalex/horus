package ua.cn.stu.oop.horus.core.io.xml;

import org.junit.Test;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;

/**
 *
 * @author alex
 */
public class ObjectInXmlFileTest {

    private ConfigScheme configScheme;

    @Test
    public void testSaveToXmlFile() throws Exception {
        configScheme = new ConfigScheme();
        fillConfigScheme();
        ObjectInXmlFile oxf = new ObjectInXmlFile(configScheme, "./cfgschm.xml");
        oxf.saveToXmlFile();
        configScheme.setId(22l);
        oxf.saveToXmlFile();
    }

    private void fillConfigScheme() {
        configScheme.setId(39l);
        configScheme.setTitle("defaultScheme");
        configScheme.setDescription("noDescription");
        configScheme.setTrainingModeOnly(false);
    }
    
    @Test
    public void testLoadFromXmlFile() throws Exception {
        configScheme = new ConfigScheme();
        ObjectInXmlFile oxf = new ObjectInXmlFile(configScheme, "/home/alex/Documents/cfgScheme.xml");
        oxf.restoreFromXmlFile();
        System.out.println(configScheme.getId());
    }
}
