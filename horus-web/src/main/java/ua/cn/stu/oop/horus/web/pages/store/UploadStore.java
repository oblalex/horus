package ua.cn.stu.oop.horus.web.pages.store;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.util.file.FileStreamResponse;

/**
 *
 * @author alex
 */
public class UploadStore {

    @Inject
    private LinkSource linkSource;

    public String getUriUploadedFile(String fileName) {
        return linkSource.createPageRenderLink("store/"+UploadStore.class.getSimpleName(), false, new Object[]{fileName}).toURI();
    }

    public StreamResponse onActivate(final String fileName) throws FileNotFoundException {
        File f = new File(ConfigContainer.CONFIG.TMP.directoryPath+"/"+fileName);
        return new FileStreamResponse(f);
    }
}
