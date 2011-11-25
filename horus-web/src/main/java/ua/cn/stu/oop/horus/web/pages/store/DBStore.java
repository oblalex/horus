package ua.cn.stu.oop.horus.web.pages.store;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.service.file.DBFileService;
import ua.cn.stu.oop.horus.web.util.file.HorusFileStreamResponse;

/**
 *
 * @author alex
 */
public class DBStore {

    @Inject
    @Autowired
    private DBFileService fileService;
    
    @Inject
    private LinkSource linkSource;

    public String getUriFileInDB(long fid) {
        return linkSource.createPageRenderLink(DBStore.class.getSimpleName(), false, new Object[]{fid}).toURI();
    }

    public StreamResponse onActivate(final long fid) {
        return new HorusFileStreamResponse(fileService.getEntityOrNullById(fid));
    }
}
