package ua.cn.stu.oop.horus.core.io.xml;

import com.thoughtworks.xstream.XStream;
import java.io.*;

/**
 * Creates a relation between object and its xml-representation in a file.
 * Allows to perform object saving and restoring operations
 * 
 * @author alex
 */
public class ObjectInXmlFile {

    private XStream xs = new XStream();
    private Object object;
    private File file;
    private SavingResult savingResult;
    private RestoringResult restoringResult;

    /**
     * @param <b>object</b> 
     *  is an object to link. Must <b>not</b> be null
     * 
     * @param <b>xmlFilePath</b>
     *  is a path to a file where the object is storing or will be stored
     * 
     */
    public ObjectInXmlFile(Object object, String xmlFilePath) {
        this.object = object;
        this.file = new File(xmlFilePath);
    }

    public RestoringResult getRestoringResult() {
        return restoringResult;
    }

    public SavingResult getSavingResult() {
        return savingResult;
    }

    /**
     * Performs saving of the object to file. Result of saving can be
     * obtained by calling {@link getSavingResult()} method 
     *
     */
    public void saveToXmlFile() {
        try {
            performSaving();
        } catch (IOException ex) {
            savingIOExceptionHandler(ex);
        }
    }

    private void performSaving() throws IOException {
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        xs.toXML(object, fos);
        fos.close();
        savingResult = SavingResult.SUCCESS;
    }

    private void savingIOExceptionHandler(IOException ex) {
        savingResult = SavingResult.ERROR_IO;
    }

    /**
     * <b>SUCCESS</b> - saving has executed successfully<br/>
     * <b>ERROR_IO</b> - {@link IOException} was caught during saving
     */
    public enum SavingResult {

        SUCCESS,
        ERROR_IO
    }

    
    /**
     * Performs restoring of the object from file. Result of restoring can be
     * obtained by calling {@link getRestoringResult()} method 
     *
     */
    public void restoreFromXmlFile() {
        try {
            performRestoring();
        } catch (FileNotFoundException ex) {
            restoringFileNotFoundExceptionHandler(ex);
        } catch (IOException ex) {
            restoringIOExceptionHandler(ex);
        }
    }

    private void performRestoring() throws IOException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        xs.fromXML(fis, object);
        fis.close();
        restoringResult = RestoringResult.SUCCESS;
    }

    private void restoringIOExceptionHandler(IOException ex) {
        restoringResult = RestoringResult.ERROR_IO;
    }

    private void restoringFileNotFoundExceptionHandler(FileNotFoundException ex) {
        restoringResult = RestoringResult.ERROR_FILE_NOT_FOUND;
    }

    /**
     * <b>SUCCESS</b> - restoring has executed successfully<br/>
     * <b>ERROR_IO</b> - {@link IOException} was caught during restoring<br/>
     * <b>ERROR_FILE_NOT_FOUND</b> - {@link FileNotFoundException} was caught during restoring
     */
    public enum RestoringResult {

        SUCCESS,
        ERROR_IO,
        ERROR_FILE_NOT_FOUND
    }
}
