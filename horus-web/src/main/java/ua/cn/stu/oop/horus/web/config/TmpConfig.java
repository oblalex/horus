package ua.cn.stu.oop.horus.web.config;

import java.io.File;
import java.io.IOException;
import ua.cn.stu.oop.horus.web.util.*;
import ua.cn.stu.oop.horus.web.util.file.HorusFileUtils;

/**
 *
 * @author alex
 */
public class TmpConfig implements Resetable {

    public String directoryPath;
    public int cleanupIntervalInSeconds;
    public int minimalFileTTLInSeconds;

    @Override
    public void reset() {
        directoryPath = WebConstants.getConstant("tmp.dir.path");
        cleanupIntervalInSeconds =
                Integer.parseInt(WebConstants.getConstant("tmp.cleanup.interval.in.seconds"));
        minimalFileTTLInSeconds =
                Integer.parseInt(WebConstants.getConstant("tmp.min.file.ttl.in.seconds"));
        resetOrCleanupDirectory();
    }

    private void resetOrCleanupDirectory() {
        File f = new File(directoryPath);
        if (f.exists()) {
            cleanUp(f);
        } else {
            f.mkdir();
        }
    }

    private void cleanUp(File f) {
        try {
            HorusFileUtils.cleanDirectory(f, minimalFileTTLInSeconds*1000);
        } catch (IOException ex) {
            onDirectoryCleanupIOException();
        }
    }

    private void onDirectoryCleanupIOException() {
    }

    public String getPathToTemporary(String name) {
        return directoryPath + "/" + System.currentTimeMillis() + "-" + name;
    }
}
