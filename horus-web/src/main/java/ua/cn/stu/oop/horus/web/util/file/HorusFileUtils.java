package ua.cn.stu.oop.horus.web.util.file;

import java.io.*;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author alex
 */
public class HorusFileUtils extends FileUtils {

    /**
     * Cleans a directory without deleting it with taking into consideration
     * minimal time-to-live for files.
     *
     * @param directory to clean
     * @param ttl - minimal file's time-to-live in seconds
     * @throws IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectory(File directory, long ttl) throws IOException {
        if (directory.isDirectory() == false) {
            String message = directory.getAbsolutePath() + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory.getAbsolutePath());
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceClean(file, ttl);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (exception != null) {
            throw exception;
        }
    }

    public static void forceClean(File file, long ttl) throws IOException {
        if (file.isDirectory()) {
            cleanDirectoryAndDeleteIfEmpty(file, ttl);
        } else {
            if (isFileLivingMoreThen(file, ttl)) {
                if (file.delete() == false) {
                    String message =
                            "Unable to delete file: " + file.getAbsolutePath();
                    throw new IOException(message);
                }
            }
        }
    }

    /**
     * Cleans a directory with taking into consideration minimal time-to-live
     * for files. If directory is empty after cleaning, then it will be deleted.
     *
     * @param directory to clean
     * @param ttl - minimal file's time-to-live in seconds
     * @throws IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectoryAndDeleteIfEmpty(File directory, long ttl) throws IOException {
        if (isSymlink(directory) == false) {
            cleanDirectory(directory, ttl);
        }

        if (isDirectoryEmpty(directory)) {
            if (directory.delete() == false) {
                String message =
                        "Unable to delete directory " + directory.getAbsolutePath() + ".";
                throw new IOException(message);
            }
        }
    }

    public static boolean isFileLivingMoreThen(File f, long ttl) {
        long time = System.currentTimeMillis() - ttl;
        return isFileOlder(f, time);
    }

    public static boolean isDirectoryEmpty(File directory) {
        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            return false;
        }
        return files.length == 0;
    }
}
