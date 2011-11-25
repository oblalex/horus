package ua.cn.stu.oop.horus.web.util.file;

import java.io.*;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

/**
 *
 * @author alex
 */
public class FileStreamResponse implements StreamResponse {

    private File file;
    private InputStream is;

    public FileStreamResponse(File file) throws FileNotFoundException {
        this.file = file;
        is = new FileInputStream(file);
    }

    @Override
    public String getContentType() {
        return FileMimeTypeChecker.getFileMimeType(file);
    }

    @Override
    public InputStream getStream() throws IOException {
        return is;
    }

    @Override
    public void prepareResponse(Response r) {
        r.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        r.setHeader("Expires", "0");
        r.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        r.setHeader("Pragma", "public");
        r.setContentLength((int)file.length());
    }
}
