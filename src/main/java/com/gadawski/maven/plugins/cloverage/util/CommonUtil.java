package com.gadawski.maven.plugins.cloverage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Common utility class
 * 
 * @author l.gadawski@gmail.com
 *
 */
public class CommonUtil {

    private static final String USER_DIR = "user.dir";

    // prevent instantiations
    private CommonUtil() {
        // empty
    }

    /**
     * @return project relative path on property "user.dir"
     */
    public static String getRelativePathToProject() {
        return System.getProperty(USER_DIR);
    }

    public static void printOutFileContent(File testFile) {
        try {
            BufferedReader reader = Files.newBufferedReader(testFile.toPath(), Charset.defaultCharset());
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
