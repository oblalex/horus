package ua.cn.stu.oop.horus.web.util.file;

import java.io.*;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;

/**
 *
 * @author alex
 */
public class HorusFileStreamResponse implements StreamResponse {

    private DBFile file;
    private InputStream is;

    public HorusFileStreamResponse(DBFile file) {
        this.file = file;
        is = new ByteArrayInputStream(file.getData());
    }

    @Override
    public String getContentType() {
        return file.getContentType();
    }

    @Override
    public InputStream getStream() throws IOException {
        return is;
    }

    @Override
    public void prepareResponse(Response r) {
        r.setHeader("Content-Disposition", "attachment; filename=" + file.getTitle());
        r.setHeader("Expires", "0");
        r.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        r.setHeader("Pragma", "public");
        r.setContentLength(file.getData().length);
    }
}
