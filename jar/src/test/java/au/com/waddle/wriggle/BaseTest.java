package au.com.waddle.wriggle;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseTest {
    protected String loadDataFile(String fileName) throws IOException {
        String contentToSend = IOUtils.toString(loadDataFileStream(fileName), "UTF-8");

        return contentToSend;
    }

    protected InputStream loadDataFileStream(String fileName) throws IOException {
        return getClass().getResourceAsStream(fileName);
    }
}
